package org.bdlions.request.handler;

import org.bdlions.transport.packet.IPacket;
import org.bdlions.session.ISession;
import org.bdlions.session.ISessionManager;
import com.bdlions.util.ACTION;
import com.bdlions.dto.response.ClientListResponse;
import com.bdlions.dto.response.ClientResponse;
import com.bdlions.dto.response.GeneralResponse;
import com.google.gson.Gson;
import java.io.File;
import org.bdlions.util.annotation.ClientRequest;
import java.util.ArrayList;
import java.util.List;
import org.bdlions.auction.dto.DTOUser;
import org.bdlions.auction.entity.EntityGender;
import org.bdlions.auction.entity.EntityRole;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.EntityUserRole;
import org.bdlions.auction.entity.manager.EntityManagerGender;
import org.bdlions.auction.entity.manager.EntityManagerRole;
import org.bdlions.auction.entity.manager.EntityManagerUser;
import org.bdlions.auction.entity.manager.EntityManagerUserRole;
import org.bdlions.auction.util.Constants;
import org.bdlions.auction.util.FileUtils;
import org.bdlions.auction.util.ServerConfig;
import org.bdlions.library.ImageLibrary;
import org.bdlions.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author nazmul hasan
 */
public class UserHandler {
    private final Logger logger = LoggerFactory.getLogger(UserHandler.class);
    private final ISessionManager sessionManager;

    public UserHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ClientRequest(action = ACTION.FETCH_USER_INFO)
    public ClientResponse getUserInfo(ISession session, IPacket packet) throws Exception 
    {
        Gson gson = new Gson();
        EntityUser entityUser = gson.fromJson(packet.getPacketBody(), EntityUser.class);
        int userId = 0;
        if(entityUser == null || entityUser.getId() == 0)
        {
            userId = (int)session.getUserId();            
        }
        else
        {
            userId = entityUser.getId();
        }        
        
        DTOUser dtoUser = new DTOUser();        
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        EntityUser entiryUser = entityManagerUser.getUserByUserId(userId);
        EntityManagerUserRole entityManagerUserRole = new EntityManagerUserRole();
        List<EntityUserRole> userRoles = entityManagerUserRole.getUserRolesByUserId(userId);
        if(userRoles != null && !userRoles.isEmpty())
        {
            List<Integer> roleIds = new ArrayList<>();
            for(EntityUserRole entityUserRole: userRoles)
            {
                roleIds.add(entityUserRole.getRoleId());
            }
            EntityManagerRole entityManagerRole = new EntityManagerRole();
            List<EntityRole> roles = entityManagerRole.getRolesByRoleIds(roleIds);
            dtoUser.setRoles(roles);
        }
        
        dtoUser.setEntityUser(entiryUser);
        
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setSuccess(true);
        clientResponse.setResult(dtoUser);        
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_USER_PROFILE_INFO)
    public ClientResponse getUserProfileInfo(ISession session, IPacket packet) throws Exception 
    {
        Gson gson = new Gson();
        EntityUser entityUser = gson.fromJson(packet.getPacketBody(), EntityUser.class);
        int userId = 0;
        if(entityUser == null || entityUser.getId() == 0)
        {
            userId = (int)session.getUserId();            
        }
        else
        {
            userId = entityUser.getId();
        }  
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        EntityUser entiryUser = entityManagerUser.getUserByUserId(userId);
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setSuccess(true);
        clientResponse.setResult(entiryUser);        
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_GENDERS)
    public ClientResponse getGenders(ISession session, IPacket packet) throws Exception 
    {
        EntityManagerGender entityManagerGender = new EntityManagerGender();
        List<EntityGender> genders = entityManagerGender.getAllGenders();             
        ClientListResponse clientListResponse = new ClientListResponse();
        clientListResponse.setList(genders);
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_USER_ROLES)
    public ClientResponse getUserRoles(ISession session, IPacket packet) throws Exception 
    {
        Gson gson = new Gson();
        EntityUser entityUser = gson.fromJson(packet.getPacketBody(), EntityUser.class);
        int userId = 0;
        if(entityUser == null || entityUser.getId() == 0)
        {
            userId = (int)session.getUserId();            
        }
        else
        {
            userId = entityUser.getId();
        }  
        List<EntityRole> roles = new ArrayList<>();
        EntityManagerUserRole entityManagerUserRole = new EntityManagerUserRole();
        List<EntityUserRole> userRoles = entityManagerUserRole.getUserRolesByUserId(userId);
        if(userRoles != null && !userRoles.isEmpty())
        {
            List<Integer> roleIds = new ArrayList<>();
            for(EntityUserRole entityUserRole: userRoles)
            {
                roleIds.add(entityUserRole.getRoleId());
            }
            EntityManagerRole entityManagerRole = new EntityManagerRole();
            roles = entityManagerRole.getRolesByRoleIds(roleIds);
        }
        
        ClientListResponse clientResponse = new ClientListResponse();
        clientResponse.setSuccess(true);
        clientResponse.setList(roles);
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_MEMBER_ROLES)
    public ClientResponse getMemberRoles(ISession session, IPacket packet) throws Exception 
    {
        List<EntityRole> memberRoleList = new ArrayList<>();
        EntityManagerRole entityManagerRole = new EntityManagerRole();
        List<EntityRole> roles = entityManagerRole.getAllRoles();
        if(roles != null && !roles.isEmpty())
        {
            for(EntityRole entityRole: roles)
            {
                if(entityRole.getId() != Constants.ROLE_ID_ADMIN)
                {
                    memberRoleList.add(entityRole);
                }
            }
        }        
        ClientListResponse clientListResponse = new ClientListResponse();
        clientListResponse.setList(memberRoleList);
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.UPDATE_USER_INFO)
    public ClientResponse updateUserInfo(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        DTOUser dtoUser = gson.fromJson(packet.getPacketBody(), DTOUser.class);
        if(dtoUser == null || dtoUser.getEntityUser() == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid user info. Please try again later.");
            return clientResponse;
        }
        if(StringUtils.isNullOrEmpty(dtoUser.getEntityUser().getFirstName()))
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("First name is required.");
            return clientResponse;
        }
        if(dtoUser.getRoles() == null || dtoUser.getRoles().isEmpty())
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Role is required.");
            return clientResponse;
        }
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        EntityUser tempEntityUser = entityManagerUser.getUserByEmail(dtoUser.getEntityUser().getEmail());
        if(tempEntityUser != null && tempEntityUser.getId() != dtoUser.getEntityUser().getId())
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Email already used or invalid.");
            return clientResponse;
        }
        List<EntityUserRole> entityUserRoles = new ArrayList<>();
        if(dtoUser.getRoles() != null && !dtoUser.getRoles().isEmpty())
        {
            for(EntityRole entityRole: dtoUser.getRoles())
            {
                EntityUserRole entityUserRole = new EntityUserRole();
                entityUserRole.setRoleId(entityRole.getId());
                entityUserRoles.add(entityUserRole);
            }
        }        
        entityManagerUser.updateUser(dtoUser.getEntityUser(), entityUserRoles);
        clientResponse.setSuccess(true);
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.UPDATE_USER_PROFILE_PICTURE)
    public ClientResponse updateUserProfilePicture(ISession session, IPacket packet) throws Exception 
    {
        Gson gson = new Gson();
        EntityUser entityUser = gson.fromJson(packet.getPacketBody(), EntityUser.class);
        if(entityUser == null)
        {
            ClientResponse clientResponse = new ClientResponse();
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid requst to update prfile picture.");
            return clientResponse;
        }
        //read image from temp directory and place into user profile picture directory
        String imageFileName = entityUser.getImg().trim().replaceAll("\n", "");
        entityUser.setImg(imageFileName);
        if(!StringUtils.isNullOrEmpty(imageFileName))
        {
            //String uploadPath = UserHandler.class.getClassLoader().getResource(Constants.SERVER_ROOT_DIR + Constants.IMAGE_UPLOAD_PATH).getFile();
            //String profilePicPath = UserHandler.class.getClassLoader().getResource(Constants.SERVER_ROOT_DIR + Constants.PROFILE_PIC_PATH).getFile();
            String uploadPath = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.IMAGE_UPLOAD_PATH;
            String profilePicPath = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.PROFILE_PIC_PATH;
            File path = new File(profilePicPath);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            //copy actual image
            FileUtils.copyFile(uploadPath + imageFileName, profilePicPath + imageFileName);
            
            //resize image to 150px to 150px
            //String profilePicPath150_150 = UserHandler.class.getClassLoader().getResource(Constants.SERVER_ROOT_DIR + Constants.IMG_PROFILE_PIC_PATH_150_150).getFile();
            String profilePicPath150_150 = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.IMG_PROFILE_PIC_PATH_150_150;
            path = new File(profilePicPath150_150);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            ImageLibrary imageLibrary = new ImageLibrary();
            imageLibrary.resizeImage(uploadPath + imageFileName, profilePicPath150_150 + imageFileName, Constants.IMG_PROFILE_PIC_WIDTH_150, Constants.IMG_PROFILE_PIC_HEIGHT_150);
            
            //resize image to 50px to 50px
            //String profilePicPath50_50 = UserHandler.class.getClassLoader().getResource(Constants.SERVER_ROOT_DIR + Constants.IMG_PROFILE_PIC_PATH_50_50).getFile();
            String profilePicPath50_50 = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.IMG_PROFILE_PIC_PATH_50_50;
            path = new File(profilePicPath50_50);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            imageLibrary.resizeImage(uploadPath + imageFileName, profilePicPath50_50 + imageFileName, Constants.IMG_PROFILE_PIC_WIDTH_50, Constants.IMG_PROFILE_PIC_HEIGHT_50);
        }
        
        int userId = 0;
        if(entityUser.getId() == 0)
        {
            userId = (int)session.getUserId();            
        }
        else
        {
            userId = entityUser.getId();
        } 
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        EntityUser eUser = entityManagerUser.getUserByUserId(userId);
        eUser.setImg(entityUser.getImg());
        entityManagerUser.updateUser(eUser);
        
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setSuccess(true);
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.UPDATE_USER_LOGO)
    public ClientResponse updateUserLogo(ISession session, IPacket packet){
        Gson gson = new Gson();
        EntityUser entityUser = gson.fromJson(packet.getPacketBody(), EntityUser.class);
        if(entityUser == null)
        {
            ClientResponse clientResponse = new ClientResponse();
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid requst to update logo.");
            return clientResponse;
        }
        //read image from temp directory and place into user logo directory
        String imageFileName = entityUser.getAgentLogo().trim().replaceAll("\n", "");
        entityUser.setAgentLogo(imageFileName);
        if(!StringUtils.isNullOrEmpty(imageFileName))
        {
            String uploadPath = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.IMAGE_UPLOAD_PATH;
            String logoPath = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.USER_LOGO_PATH;
            File path = new File(logoPath);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            //copy actual image
            FileUtils.copyFile(uploadPath + imageFileName, logoPath + imageFileName);            
            
            //resize image to 100px to 100px
            String logoPath100_100 = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.USER_LOGO_PATH_100_100;
            path = new File(logoPath100_100);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            ImageLibrary imageLibrary = new ImageLibrary();
            imageLibrary.resizeImage(uploadPath + imageFileName, logoPath100_100 + imageFileName, Constants.IMG_PROFILE_LOGO_WIDTH_100, Constants.IMG_PROFILE_LOGO_HEIGHT_100);
            
            
        }
        int userId = 0;
        if(entityUser.getId() == 0)
        {
            userId = (int)session.getUserId();            
        }
        else
        {
            userId = entityUser.getId();
        } 
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        EntityUser eUser = entityManagerUser.getUserByUserId(userId);
        eUser.setAgentLogo(entityUser.getAgentLogo());
        entityManagerUser.updateUser(eUser);
        
        GeneralResponse response = new GeneralResponse();
        response.setMessage("Logo is updated successfully.");
        response.setSuccess(true);
        return response;
    }
    
    @ClientRequest(action = ACTION.UPDATE_USER_DOCUMENT)
    public ClientResponse updateUserDocument(ISession session, IPacket packet){
        Gson gson = new Gson();
        EntityUser entityUser = gson.fromJson(packet.getPacketBody(), EntityUser.class);
        if(entityUser == null)
        {
            ClientResponse clientResponse = new ClientResponse();
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid requst to update document.");
            return clientResponse;
        }
        //read image from temp directory and place into user logo directory
        String imageFileName = entityUser.getDocument().trim().replaceAll("\n", "");
        entityUser.setDocument(imageFileName);
        if(!StringUtils.isNullOrEmpty(imageFileName))
        {
            String uploadPath = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.IMAGE_UPLOAD_PATH;
            String documentPath = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.USER_DOCUMENT_PATH;
            File path = new File(documentPath);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            //copy actual image
            FileUtils.copyFile(uploadPath + imageFileName, documentPath + imageFileName);
        }
        int userId = 0;
        if(entityUser.getId() == 0)
        {
            userId = (int)session.getUserId();            
        }
        else
        {
            userId = entityUser.getId();
        } 
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        EntityUser eUser = entityManagerUser.getUserByUserId(userId);
        eUser.setDocument(entityUser.getDocument());
        entityManagerUser.updateUser(eUser);
        
        GeneralResponse response = new GeneralResponse();
        response.setMessage("Logo is updated successfully.");
        response.setSuccess(true);
        return response;
    }
}
