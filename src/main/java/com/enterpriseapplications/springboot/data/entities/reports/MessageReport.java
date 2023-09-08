package com.enterpriseapplications.springboot.data.entities.reports;


import com.enterpriseapplications.springboot.data.entities.Message;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Table(name = "MESSAGE_REPORTS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReport extends Report {
    @ManyToOne(fetch = FetchType.LAZY)
    private Message message;
}
