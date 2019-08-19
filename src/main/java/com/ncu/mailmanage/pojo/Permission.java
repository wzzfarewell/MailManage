package com.ncu.mailmanage.pojo;

public class Permission {
    private Long permissionId;

    private String permission;

    private String resName;

    private String url;

    public Permission(Long permissionId, String permission, String resName, String url) {
        this.permissionId = permissionId;
        this.permission = permission;
        this.resName = resName;
        this.url = url;
    }

    public Permission() {
        super();
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}