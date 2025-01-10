package TaskOrganiser;

public class MainClient_accounts {
    private String Username;
    private String Password;
    private String CName;
    private String Status;

    public String getUsername (){
        return Username;
    }
    public void setUsername(String Username){
        this.Username= Username;
    }

    public String getPassword (){
        return Password;
    }
    public void setPassword(String Password){
        this.Password= Password;
    }

    public String getCName (){
        return CName;
    }
    public void setCName(String CName){
        this.CName= CName;
    }

    public String getStatus (){
        return Status;
    }
    public void setStatus(String Status){
        this.Status= Status;
    }
}
