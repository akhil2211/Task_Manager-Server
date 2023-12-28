package com.example.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor

public enum UserRoles {
    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_DELETE
            )
    ),

    GM(
            Set.of(
                    Permission.GM_READ,
                    Permission.GM_CREATE,
                    Permission.GM_DELETE,
                    Permission.GM_UPDATE

            )
    ),

    PM(
         Set.of(
                 Permission.PM_CREATE,
                 Permission.PM_READ,
                 Permission.PM_DELETE,
                 Permission.PM_UPDATE
         )

    ),

    USER(Collections.emptySet());

    ;
    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
      var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
      authorities.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
      return authorities;

    }


}
