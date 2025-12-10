package paymentservice.using.dto;

import com.valetparker.chagok.using.enums.UsingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsingDto {

    private long usingId;
    private UsingStatus usingStatus;
    private int exceededCount;
    private long reservationId;
}
