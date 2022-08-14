package com.lhmgiw.jpametamodel.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponseDTO<T> {
    private T message;
    private LocalDateTime dateTime;
    private String details;
}
