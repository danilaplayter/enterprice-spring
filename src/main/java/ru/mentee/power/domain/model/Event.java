/* (C) 2025 MENTEE POWER */

package ru.mentee.power.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "total_seats")
  private int totalSeats;

  @Column(name = "booked_seats")
  private int bookedSeats;

  @Version private Long version;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private List<Seat> seats = new ArrayList<>();
}
