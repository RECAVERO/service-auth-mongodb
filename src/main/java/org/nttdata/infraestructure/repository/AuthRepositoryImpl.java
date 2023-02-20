package org.nttdata.infraestructure.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.Document;
import org.nttdata.domain.contract.AuthRepository;
import org.nttdata.domain.models.AuthDto;
import org.nttdata.infraestructure.entity.Auth;

import javax.enterprise.context.ApplicationScoped;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped
public class AuthRepositoryImpl implements AuthRepository {
  private final ReactiveMongoClient reactiveMongoClient;

  public AuthRepositoryImpl(ReactiveMongoClient reactiveMongoClient) {
    this.reactiveMongoClient = reactiveMongoClient;
  }

  @Override
  public Multi<AuthDto> list() {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("auths");
    ReactiveMongoCollection<Document> collection = database.getCollection("auth");
    return collection.find().map(doc->{
      AuthDto authDto = new AuthDto();
      authDto.setNumberCard(doc.getString("numberCard"));
      authDto.setPassword(doc.getInteger("password"));
      authDto.setCreated_datetime(doc.getString("created_datetime"));
      authDto.setUpdated_datetime(doc.getString("updated_datetime"));
      authDto.setActive(doc.getString("active"));
      return authDto;
    }).filter(auth->{
      return auth.getActive().equals("S");
    });
  }

  @Override
  public Uni<AuthDto> findByNroAuth(AuthDto authDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("auths");
    ReactiveMongoCollection<Document> collection = database.getCollection("auth");

    return collection
        .find(new Document("numberCard", authDto.getNumberCard())).map(doc->{
          AuthDto auth = new AuthDto();
          auth.setNumberCard(doc.getString("numberCard"));
          auth.setPassword(doc.getInteger("password"));
          auth.setCreated_datetime(doc.getString("created_datetime"));
          auth.setUpdated_datetime(doc.getString("updated_datetime"));
          auth.setActive(doc.getString("active"));
          return auth;
        }).filter(s->s.getActive().equals("S")).toUni();
  }

  @Override
  public Uni<AuthDto> addAuth(AuthDto authDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("auths");
    ReactiveMongoCollection<Document> collection = database.getCollection("auth");
    Document document = new Document()
        .append("numberCard", authDto.getNumberCard())
        .append("password", authDto.getPassword())
        .append("created_datetime", this.getDateNow())
        .append("updated_datetime", this.getDateNow())
        .append("active", "S");
    return collection.insertOne(document).replaceWith(authDto);
  }

  @Override
  public Uni<AuthDto> updateAuth(AuthDto authDto) {
    return null;
  }

  @Override
  public Uni<AuthDto> deleteAuth(AuthDto authDto) {
    return null;
  }
  private static String getDateNow(){
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date).toString();
  }

  public static Auth mapToEntity(Object accountDto) {
    return new ObjectMapper().convertValue(accountDto, Auth.class);
  }
  public static AuthDto mapToDto(Object accountDto) {
    return new ObjectMapper().convertValue(accountDto, AuthDto.class);
  }

  public static AuthDto mapToDomain(Auth auth) {
    return new ObjectMapper().convertValue(auth, AuthDto.class);
  }


}
