package org.jzeratul.mykid.model;

import java.time.OffsetDateTime;

public record UserRecord(
        Long id,
        String username,
        String password,
        OffsetDateTime createdAt
) {
}
