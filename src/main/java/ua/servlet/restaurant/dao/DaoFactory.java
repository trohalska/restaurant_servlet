package ua.servlet.restaurant.dao;

import ua.servlet.restaurant.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract LoginsDao createLoginsDao();
//    public abstract StudentDao createStudentDao();

    public static DaoFactory getInstance() {
        if( daoFactory == null ){
            synchronized (DaoFactory.class) {
                if(daoFactory == null){
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
