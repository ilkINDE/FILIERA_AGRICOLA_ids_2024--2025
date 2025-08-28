package unicam.filiera_agricola_ids_20242025.DTO;

import jakarta.validation.constraints.*;

    public record RichiestaTrasformatoreDTO(
            @NotBlank String nome,
            @NotNull @Digits(integer = 11, fraction = 0) Long piva,
            @NotBlank String processoDiTrasformazione
    ) {}

