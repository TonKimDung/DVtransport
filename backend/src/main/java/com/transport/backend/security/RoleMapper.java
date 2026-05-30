package com.transport.backend.security;

public class RoleMapper {

    public static String toRoleCode(String roleName) {
        if (roleName == null) return "";

        return switch (roleName.trim()) {
            case "Admin" -> "ADMIN";
            case "Điều phối viên" -> "DIEU_PHOI_VIEN";
            case "HR" -> "HR";
            case "Lái xe" -> "LAI_XE";
            case "Kế toán" -> "KE_TOAN";
            default -> roleName.toUpperCase();
        };
    }
}