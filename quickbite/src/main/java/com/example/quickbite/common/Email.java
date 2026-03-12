package com.example.quickbite.common;

import java.util.UUID;

public record Email(
        String type,
        String email,
        UUID confirmationToken
        ) {

}
