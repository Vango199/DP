package gui;

public interface Command {
    
    void execute();
    void redo();
    void compensate();
}

