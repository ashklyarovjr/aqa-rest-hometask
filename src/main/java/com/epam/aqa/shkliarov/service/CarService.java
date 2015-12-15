package com.epam.aqa.shkliarov.service;

import com.epam.aqa.shkliarov.dao.CarDAO;
import com.epam.aqa.shkliarov.entity.CarEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cars")
public class CarService {
    private CarDAO dao = new CarDAO();

    @GET
    @Path("/car/{param}")
    public Response get(@PathParam("param") int id) {
        CarEntity entity = dao.get(id);
        return Response.status(200).entity(entity).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/allcars")
    public Response getAll() {
        List entityList = dao.getAll();
        return Response.status(200).entity(entityList.toString()).type(MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/car/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("param") int id, CarEntity countryEntity) {
        dao.put(id, countryEntity);
        return Response.status(200).entity("Country has been successfully updated").build();
    }

    @DELETE
    @Path("/country/{param}")
    public Response delete(@PathParam("param") int id) {
        dao.delete(id);
        return Response.status(200).entity("country has been successfully deleted").build();
    }

    @POST
    @Path("/car")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(CarEntity carEntity) {
        if (dao.get(carEntity.getId()) == null) {
            dao.save(carEntity);
        } else {
            return Response.status(409).entity("Country with id " + carEntity.getId() + " already exists. Try PUT method").build();
        }
        return Response.status(200).entity("Country has been successfully added").build();
    }

    @POST
    @Path("/allcars")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveAll(List<CarEntity> carEntities) {
        for (CarEntity carEntity : carEntities) {
            if (dao.get(carEntity.getId()) == null) {
                dao.save(carEntity);
            } else {
                return Response.status(409).entity("Country with id " + carEntity.getId() + " already exists. Try PUT method").build();
            }
        }
        return Response.status(200).entity("Countries have been successfully added").build();
    }


}
