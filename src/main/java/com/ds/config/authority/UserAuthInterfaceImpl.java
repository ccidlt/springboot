package com.ds.config.authority;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserAuthInterfaceImpl implements UserAuthInterface {

    @Override
    public UserAuth getUserAuth(String authKey) {
        UserAuth userAuth = new UserAuth();
        //通过authKey和当前用户userId，获取到当前用户角色绑定的菜单的authKey对应的权限值data(当前用户id可以从token中获取)
        Integer authData = 1;
        if (!Objects.isNull(authData)) {
            switch (DataScopeViewTypeEnum.getByValue(authData)) {
                case VIEW_NONE:
                    userAuth.setNone(Boolean.TRUE);
                    break;
                case VIEW_ALL:
                    userAuth.setAll(Boolean.TRUE);
                    break;
                case VIEW_COMPANY_AND_SUB:
                    userAuth.setIds(new ArrayList<Integer>(3) {{
                        add(1);
                        add(2);
                        add(3);
                    }});
                    break;
                case VIEW_COMPANY:
                    break;
                default:
                    break;
            }
        }
        return userAuth;
    }
}
