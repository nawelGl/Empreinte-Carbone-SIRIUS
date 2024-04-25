package edu.ezip.ing1.pds.business.dto;


import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BestSeller {

    private int reference1;
    private int reference2;
    private int reference3;

    private String score1;
    private String score2;
    private String score3;

    private int sum1;
    private int sum2;
    private int sum3;



    public BestSeller() {
    }

    public final BestSeller build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet,  "reference1","score1","sum1","reference2","score2","sum2","reference3","score3","sum3");
        return this;
    }


    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, reference1,score1,sum1,reference2,score2,sum2,reference3,score3,sum3);
    }

    public int getReference1() {
        return reference1;
    }

    public void setReference1(int reference1) {
        this.reference1 = reference1;
    }

    public int getReference2() {
        return reference2;
    }

    public void setReference2(int reference2) {
        this.reference2 = reference2;
    }

    public int getReference3() {
        return reference3;
    }

    public void setReference3(int reference3) {
        this.reference3 = reference3;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getScore3() {
        return score3;
    }

    public void setScore3(String score3) {
        this.score3 = score3;
    }

    public int getSum1() {
        return sum1;
    }

    public void setSum1(int sum){this.sum1=sum1;}

    public int getSum2() {
        return sum1;
    }
    public void setSum2(int sum){this.sum2=sum2;}


    public void setSum3(int sum){this.sum3=sum3;}

    public int getSum3() {
        return sum3;
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
        return "BestSeller{" +
                "reference1='" + reference1 + '\'' +','+
                "score1='" + score1 + '\'' +','+
                "sum1='" + sum1 + '\''+
                "reference2='" + reference2 + '\'' +','+
                "score2='" + score2 + '\'' +','+
                "sum2='" + sum2 + '\''+
                "reference3='" + reference3 + '\'' +','+
                "score3='" + score3 + '\'' +','+
                "sum3='" + sum3 + '\''+

                '}';
    }

}