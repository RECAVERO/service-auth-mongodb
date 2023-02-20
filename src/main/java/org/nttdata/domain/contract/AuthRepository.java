package org.nttdata.domain.contract;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.nttdata.domain.models.AuthDto;

public interface AuthRepository {
  Multi<AuthDto> list();

  Uni<AuthDto> findByNroAuth(AuthDto authDto);

  Uni<AuthDto> addAuth(AuthDto authDto);

  Uni<AuthDto> updateAuth(AuthDto authDto);

  Uni<AuthDto> deleteAuth(AuthDto authDto);
}
