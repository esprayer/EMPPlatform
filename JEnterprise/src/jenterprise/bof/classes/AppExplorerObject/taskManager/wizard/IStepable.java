package jenterprise.bof.classes.AppExplorerObject.taskManager.wizard;

/**
 * 向导步骤检查.
 * @version 1.0
 */
public interface IStepable {
    boolean isValid();
    String getInvalidMessage();
    Object getSelectedInfo();
}
