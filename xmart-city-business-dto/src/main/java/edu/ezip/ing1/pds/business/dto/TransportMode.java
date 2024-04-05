package edu.ezip.ing1.pds.business.dto;

import java.lang.reflect.Field;
import java.net.IDN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransportMode {
    private int idTransportMode;
    private double coeffEmission;
    private String nomTransport;
    private String carburant ;

    public TransportMode(){

    }

    public TransportMode(int idTransportMode, double coeffEmission, String nomTransport, String carburant){
        this.idTransportMode= idTransportMode;
        this.coeffEmission=coeffEmission;
        this.nomTransport=nomTransport;
        this.carburant=carburant;
    }

    // Getters and setters for each field


    public int getIdTransportMode() {
        return idTransportMode;
    }

    public double getCoeffEmission() {
        return coeffEmission;
    }

    public String getCarburant() {
        return carburant;
    }

    public String getNomTransport() {
        return nomTransport;
    }

    public void setIdTransportMode(int idTransportMode) {
        this.idTransportMode = idTransportMode;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public void setCoeffEmission(double coeffEmission) {
        this.coeffEmission = coeffEmission;
    }

    public void setNomTransport(String nomTransport) {
        this.nomTransport = nomTransport;
    }

    public final TransportMode build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idTransportMode","carburant", "nomTransport", "coeffEmission");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idTransportMode,carburant, nomTransport, coeffEmission);
    }

    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final Object... fieldValues)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        int ix = 0;
        for (final Object fieldValue : fieldValues) {
            preparedStatement.setObject(++ix, fieldValue);
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "TransportMode{" +
                "idTransportMode=" + idTransportMode +
                ", carburant ='" + carburant + '\'' +
                ", coeffEmission ='" + coeffEmission + '\'' +
                ", nomTransport ='" +nomTransport + '\'' +
                '}';
    }

}

