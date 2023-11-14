public class _Interactor implements _InputBoundary {
    private _DataAccessInterface dataAccessObject;
    private _OutputBoundary presenter;

    public _Interactor(_DataAccessInterface dataAccessObject, _OutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    public void execute(_InputData inputData) {

    } 
}
