package io.fdlessard.codebites.orders;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class SoftDeletedRequestFilter extends OncePerRequestFilter {

    private final EntityManager entityManager;

    public SoftDeletedRequestFilter(@Lazy EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final boolean includeDeleted = "true".equalsIgnoreCase(request.getParameter("includeDeleted"));
        final boolean onlyDeleted = "true".equalsIgnoreCase(request.getParameter("onlyDeleted"));

        Session session = entityManager.unwrap(Session.class);

        if (onlyDeleted) {
            session.enableFilter("deletedOnlyFilter");
            logger.info("FILTER: deletedOnlyFilter enabled");
        } else if (!includeDeleted) {
            session.enableFilter("notDeletedFilter");
            logger.info("FILTER: notDeletedFilter enabled");
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            if (session.getEnabledFilter("deletedOnlyFilter") != null) {
                session.disableFilter("deletedOnlyFilter");
            }
            if (session.getEnabledFilter("notDeletedFilter") != null) {
                session.disableFilter("notDeletedFilter");
            }
        }

    }
}
