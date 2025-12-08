package com.valetparker.reviewservice.command.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewCommandResponse {
    private Long reviewId;
}