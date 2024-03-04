package com.challenge.backend.ecommerce.dto;

import java.util.Date;

public record ApplicationUserResponseDto(String name, String surname, String email, Date date) {
}
