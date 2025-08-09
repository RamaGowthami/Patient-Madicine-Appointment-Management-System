package com.circleconnect.security;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.auth.oauth2.GoogleCredentials;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Component
public class FirebaseAuthFilter extends OncePerRequestFilter {

    @Value("${app.security.firebase-admin-credentials:}")
    private String firebaseAdminCredentialsPath;

    private volatile boolean firebaseInitialized = false;

    @PostConstruct
    public void initFirebase() {
        if (StringUtils.hasText(firebaseAdminCredentialsPath)) {
            try (FileInputStream serviceAccount = new FileInputStream(firebaseAdminCredentialsPath)) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options);
                firebaseInitialized = true;
            } catch (Exception e) {
                // leave uninitialized; dev mode will be used
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        try {
            if (StringUtils.hasText(authHeader)) {
                if (authHeader.startsWith("Bearer ")) {
                    String idToken = authHeader.substring(7);
                    if (firebaseInitialized) {
                        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                        setAuthentication(decodedToken.getUid());
                    } else {
                        // When not initialized, treat any Bearer as dev user (not recommended beyond local dev)
                        setAuthentication("dev-user");
                    }
                } else if (authHeader.startsWith("Dev ")) {
                    // Dev mode: Authorization: Dev uid=<uid>
                    String[] parts = authHeader.substring(4).trim().split("\\s+");
                    String uid = "dev-user";
                    for (String part : parts) {
                        if (part.startsWith("uid=")) {
                            uid = part.substring(4);
                            break;
                        }
                    }
                    setAuthentication(uid);
                }
            }
        } catch (Exception e) {
            // ignore and continue without authentication
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String uid) {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(uid, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}