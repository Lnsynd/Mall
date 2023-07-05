package com.lqs.mall.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Minio桶策略配置
 * Created by 刘千山 on 2023/7/5/005-19:31
 */
@Data
@EqualsAndHashCode
@Builder
public class BucketPolicyConfigDto {
    private String Version;
    private List<Statement> Statement;

    @Data
    @EqualsAndHashCode
    @Builder
    public static class Statement {
        private String Effect;
        private String Principal;
        private String Action;
        private String Resource;

    }
}
