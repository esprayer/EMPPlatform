package jenterprise.bof.classes.AppExplorerObject.taskManager.module;

/**
 * 任务参数设置接口。
 *
 * 针对具体的模块，每个任务的参数是不定的。
 * 通过这个接口，实现各个模块的参数的统一数据格式。
 *
 * @version 1.0 初始版本${gengeng}
 */
public interface ITaskParamable {
    Object getParamObject();
    void setParamObject(Object paramObj);
    String getErrorMessage();
}
