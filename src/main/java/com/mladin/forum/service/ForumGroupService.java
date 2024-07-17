package com.mladin.forum.service;

import com.mladin.forum.entity.ForumGroupEntity;
import com.mladin.forum.repository.ForumGroupRepository;
import com.mladin.forum.security.ForumGroup;
import com.mladin.forum.utils.ForumGZipCompression;
import com.mladin.forum.utils.ForumUUIDPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

@Component
public class ForumGroupService {
    private Logger logger = Logger.getLogger(this.getClass().getName());

   private HashMap<String, ForumGroup> groups = new HashMap<>();

    @Autowired
    private ForumGroupRepository forumGroupRepository;

    public boolean groupExists(String groupName) {
        return forumGroupRepository.existsByName(groupName);
    }

    public boolean groupLoaded(String groupName) {
        return groups.containsKey(groupName);
    }

    public ForumGroup getGroup(String groupName) {
        return groups.get(groupName);
    }

    public void loadAllGroups() {
        List<ForumGroupEntity> groupsEntity = forumGroupRepository.findAll();
        if(groupsEntity.size() != 0) {
            logger.info("Found " + groupsEntity.size() + " groups.");

            groupsEntity.forEach(groupEntity -> {
                loadGroup(groupEntity);
            });
        }else {
            logger.info("No groups found.");
        }
    }

    public void loadGroup(ForumGroupEntity groupEntity) {
        List<String> authorities = authorities(new AtomicBoolean(), groupEntity.getAuthorities());

        ForumGroup forumGroup = new ForumGroup(groupEntity, Integer.parseInt(authorities.get(0)), grantedAuthorities(authorities));
        groups.put(forumGroup.getName(), forumGroup);

        logger.info("Loaded group " + forumGroup.getName() + ", priority " + forumGroup.getPriority() + " with " + forumGroup.getGrantedAuthorities().size() + " authorities.");
    }

    public boolean addAuthority(String groupName, String authority) {
        ForumGroup forumGroup = getGroup(groupName);
        forumGroup.getGrantedAuthorities().add(new SimpleGrantedAuthority(authority));

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        return updateAuthorities(forumGroup, atomicBoolean);
    }

    public boolean existsAuthority(String groupName, String authority) {
        ForumGroup forumGroup = getGroup(groupName);

        return forumGroup.getGrantedAuthorities().contains(new SimpleGrantedAuthority(authority));
    }

    public boolean deleteAuthority(String groupName, String authority) {
        ForumGroup forumGroup = getGroup(groupName);
        forumGroup.getGrantedAuthorities().remove(new SimpleGrantedAuthority(authority));

        AtomicBoolean atomicBoolean = new AtomicBoolean();
        return updateAuthorities(forumGroup, atomicBoolean);
    }

    public boolean updateAuthorities(ForumGroup forumGroup, AtomicBoolean atomicBoolean) {
        byte[] authorities = convertGrantedAuthoritiesToByteArray(atomicBoolean, forumGroup.getPriority(), forumGroup.getGrantedAuthorities());

        if(!atomicBoolean.get()) {
            try {
                forumGroupRepository.setAuthoritiesByName(forumGroup.getName(), authorities);

                return true;
            }catch (Exception exception) {
                exception.printStackTrace();

                return false;
            }
        }else {
            return false;
        }
    }

    public boolean createEmptyGroup(String name, String display, String priority) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        ForumGroupEntity groupEntity = new ForumGroupEntity(ForumUUIDPool.grabNewUUID(forumGroupRepository).toString(), name, display, defaultAuthorities(atomicBoolean, priority));

        if(!atomicBoolean.get()) {
            forumGroupRepository.save(groupEntity);
        }

        return !atomicBoolean.get();
    }

    public List<GrantedAuthority> grantedAuthorities(List<String> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(int i = 1; i < authorities.size(); i++) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authorities.get(i)));
        }

        return grantedAuthorities;
    }

    public byte[] defaultAuthorities(AtomicBoolean error, String priority) {
        try {
            List<String> authorities = new ArrayList<>();
            authorities.add(priority);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(authorities);
            objectOutputStream.flush();

            error.set(false);

            byte[] raw = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();

            return ForumGZipCompression.compress(Base64.getEncoder().encode(raw));
        }catch (Exception exception) {
            exception.printStackTrace();

            error.set(true);

            return new byte[0];
        }
    }

    public byte[] convertGrantedAuthoritiesToByteArray(AtomicBoolean error, int priority, List<GrantedAuthority> authorities) {
        try {
            List<String> authorities_data = new ArrayList<>();
            authorities_data.add(String.valueOf(priority));

            authorities.forEach(authority-> {
                authorities_data.add(authority.getAuthority());
            });

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(authorities_data);

            objectOutputStream.flush();

            byte[] raw = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.close();
            objectOutputStream.close();

            return ForumGZipCompression.compress(Base64.getEncoder().encode(raw));
        }catch (Exception exception) {
            exception.printStackTrace();

            error.set(true);

            return new byte[0];
        }
    }

    public List<String> authorities(AtomicBoolean error, byte[] data) {
        try {
            byte[] decompressed = ForumGZipCompression.decompress(data);

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(decompressed)));

            error.set(false);

            return (List<String>) objectInputStream.readObject();
        }catch (Exception exception) {
            exception.printStackTrace();

            error.set(true);

            return new ArrayList<>();
        }
    }

    public HashMap<String, ForumGroup> getGroups() {
        return this.groups;
    }
}
