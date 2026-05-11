package com.example.myapplication.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "claims")
public class Claim {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "policy_number")
    private String policyNumber;

    @ColumnInfo(name = "claim_amount")
    private double claimAmount;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "is_synced")
    private boolean is_synced;

    public Claim() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSynced() {
        return is_synced;
    }

    public void isSynced(boolean synced) {
        this.is_synced = synced;
    }
}
