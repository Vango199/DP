package gui;

import javax.annotation.processing.Completions;

import nutsAndBolts.ChessGUIConfig;
import java.awt.Color;

public class CommandLightColor implements Command{


    private Color previousColor =null; 
    private Color newColor =null;

    public CommandLightColor(Color _newColor){
        this.newColor= _newColor;
    }

    @Override
    public void execute() {
        this.previousColor = ChessGUIConfig.getLightColor();
        ChessGUIConfig.setLightColor(newColor);
        
    }

    @Override
    public void compensate() {
        ChessGUIConfig.setLightColor(previousColor);
        
    }

    @Override
    public void redo() {
        ChessGUIConfig.setLightColor(newColor);
        
    }

    
}
