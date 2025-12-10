package paymentservice.using.domain;

import com.valetparker.chagok.using.enums.UsingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "tbl_using")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Using {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long usingId;

    private boolean isQuit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("BEFORE")
    private UsingStatus usingStatus;

    @Column(nullable = false)
    private int exceededCount;

    private Long reservationId;

    public void settingUsingStatus(UsingStatus usingStatus) {
        if (this.usingStatus != usingStatus) {
            this.usingStatus = usingStatus;
        }
    }

    public void setUsingStatus(UsingStatus usingStatus) {
        this.usingStatus = usingStatus;
    }

    public void exceededCounter(int exceededCount) {
        this.exceededCount = exceededCount;
    }

    public void setIsQuit(boolean quit) {     // is_quit 컬럼에 매핑
        this.isQuit = quit;
    }


}
