package TaskOrganiser;

public class MainClient_portal {
    private String commentCLI;
    private String details;
    private String dueDate;
    private String priority;
    private String Tstatus;

    public String getCommentCLI (){
        return commentCLI;
    }
    public void setCommentCLI(String commentCLI){
        this.commentCLI= commentCLI;
    }

    public String getDetails (){
        return details;
    }
    public void setDetails(String details){
        this.details= details;
    }

    public String getDueDate (){
        return dueDate;
    }
    public void setDueDate(String dueDate){
        this.dueDate= dueDate;
    }

    public String getPriority(){
        return priority;
    }
    public void setPriority(String priority){
        this.priority=priority;
    }
    public String getTstatus(){return Tstatus;}

    public void setTstatus(String Tstatus){
        this.Tstatus=Tstatus;
    }

}
