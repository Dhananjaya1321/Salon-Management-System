package lk.ijse.salongeetha.model.castom.impl;

import lk.ijse.salongeetha.model.CrudUtil;
import lk.ijse.salongeetha.to.ServiceAppointmentDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceAppointmentModel {

    public static boolean addDetails(ArrayList<ServiceAppointmentDetail> serviceAppointmentDetails) throws SQLException, ClassNotFoundException {
        int i = 0;
        for (ServiceAppointmentDetail a : serviceAppointmentDetails) {
            boolean isAdded = CrudUtil.setQuery("INSERT INTO service_appointment_detail VALUES (?,?)", a.getAptId(), a.getSevId());
            if (isAdded) {
                i++;
            }
        }

        if (i == serviceAppointmentDetails.size()) {
            return true;
        }
        return false;
    }

    public static ArrayList<ServiceAppointmentDetail> getAmountDue(ServiceAppointmentDetail serviceAppointmentDetail) throws SQLException, ClassNotFoundException {
        ArrayList<ServiceAppointmentDetail> serviceAppointmentDetails = new ArrayList<>();
        ResultSet resultSet = CrudUtil.setQuery("select s.price,s.discount from service_appointment_detail ad join service s on ad.sev_Id = s.sev_Id where Apt_Id=?", serviceAppointmentDetail.getAptId());
        while (resultSet.next()) {
            ServiceAppointmentDetail setServiceAppointmentDetail = new ServiceAppointmentDetail();
            setServiceAppointmentDetail.setPrice((Double) resultSet.getObject(1));
            setServiceAppointmentDetail.setDiscount((Double) resultSet.getObject(2));
            serviceAppointmentDetails.add(setServiceAppointmentDetail);
        }
        return serviceAppointmentDetails;
    }
}
