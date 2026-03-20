package com.wuhk.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.support.NameUtils;

import javax.validation.ValidationException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * @className: GatewayPredicateDefinition
 * @description: 4.断言规则
 * @author: wuhk
 * @date: 2023/8/22 10:48
 * @version: 1.0
 * @company 航天信息
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayPredicateDefinition {
    /**
     * 断言对应的name
     */
    private String name;
    /**
     * 配置 断言规则
     */
    private Map<String, String> args = new LinkedHashMap<>();

    public GatewayPredicateDefinition(String text) {
        int eqIdx = text.indexOf('=');
        if (eqIdx <= 0) {
            throw new ValidationException("Unable to parse PredicateDefinition text '"
                    + text + "'" + ", must be of the form name=value");
        }
        setName(text.substring(0, eqIdx));

        String[] args = tokenizeToStringArray(text.substring(eqIdx + 1), ",");

        for (int i = 0; i < args.length; i++) {
            this.args.put(NameUtils.generateName(i), args[i]);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

    public void addArg(String key, String value) {
        this.args.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GatewayPredicateDefinition that = (GatewayPredicateDefinition) o;
        return Objects.equals(name, that.name) && Objects.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, args);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PredicateDefinition{");
        sb.append("name='").append(name).append('\'');
        sb.append(", args=").append(args);
        sb.append('}');
        return sb.toString();
    }
}
