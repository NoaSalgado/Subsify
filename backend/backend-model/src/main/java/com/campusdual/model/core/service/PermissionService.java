package com.campusdual.model.core.service;

import com.campusdual.api.core.service.IPermissionService;
import com.campusdual.model.core.dao.UserDao;
import com.campusdual.model.core.dao.UserRoleDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("PermissionService")
@Lazy
public class PermissionService implements IPermissionService {
    public final String ADMIN_PERMISSION;
    public final String USER_PERMISSION;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;
    @Autowired
    private UserRoleDao userRoleDao;

    private String readFromInputStream(String fileName) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(fileName))))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        catch (IOException e){
            return "";
        }
        return resultStringBuilder.toString();
    }

    public PermissionService (){
        try {
            ADMIN_PERMISSION = readFromInputStream("admin_permissions.json");
            USER_PERMISSION = readFromInputStream("user_permissions.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EntityResult permissionQuery(Map<String, Object> keyMap, List<String> attrList) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EntityResult e = new EntityResultMapImpl();
        Map<String, String> map = new HashMap<>();
        String role = authentication.getAuthorities().toArray()[0].toString();

        if (role.equals("user")) {
            map.put("permission", USER_PERMISSION);
        }
        else if (role.equals("admin")) {
            map.put("permission", ADMIN_PERMISSION);
        }
        e.addRecord(map);
        return e;
    }

    public EntityResult userRoleQuery(Map<String, Object> keyMap, List<String> attrList) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> newKeyValues = new HashMap<>(keyMap);
        String username = authentication.getName();
        newKeyValues.put(UserDao.ID,username);

        return this.daoHelper.query(this.userRoleDao, newKeyValues, attrList, UserRoleDao.userRoleQuery);
    }
}