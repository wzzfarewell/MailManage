package com.ncu.mailmanage.pojo;

public class Role {
    private Long roleId;

    private String roleName;

    private String roleNameZhcn;

    public Role(Long roleId, String roleName, String roleNameZhcn) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleNameZhcn = roleNameZhcn;
    }

    public Role() {
        super();
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleNameZhcn() {
        return roleNameZhcn;
    }

    public void setRoleNameZhcn(String roleNameZhcn) {
        this.roleNameZhcn = roleNameZhcn == null ? null : roleNameZhcn.trim();
    }
}