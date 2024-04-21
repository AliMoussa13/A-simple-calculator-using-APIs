package API;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import EJB.Equation;

@Stateless
@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Service {

	@PersistenceContext(unitName="hello")
	private EntityManager entityManager;
	
	@POST
	@Path("/calc")
	public Response CreateCalculation(Equation equation) {
		try {
		Response response = null;
		
		if (equation == null) {
	        return Response.status(Response.Status.BAD_REQUEST).entity("Invalid input").type(MediaType.APPLICATION_JSON).build();
	    }
		
		if(equation.getOperation().equals("+")) {
			int result = equation.getNumber1() + equation.getNumber2();
			response = Response.ok().entity("{\"Result\": " + result + "}").type(MediaType.APPLICATION_JSON).build();
		} 
		else if(equation.getOperation().equals("-")) {
			int result = equation.getNumber1() - equation.getNumber2();
			response = Response.ok().entity("{\"Result\": " + result + "}").type(MediaType.APPLICATION_JSON).build();
		} 
		else if(equation.getOperation().equals("*")) {
			int result = equation.getNumber1() * equation.getNumber2();
			response = Response.ok().entity("{\"Result\": " + result + "}").type(MediaType.APPLICATION_JSON).build();
		} 
		else if(equation.getOperation().equals("/")) {
			if(equation.getNumber2() == 0)
				response = Response.status(Response.Status.BAD_REQUEST).entity("Can't divide by zero").type(MediaType.APPLICATION_JSON).build();
			else {
			double result = (double) equation.getNumber1() / equation.getNumber2();
			response = Response.ok().entity("{\"Result\": " + result + "}").type(MediaType.APPLICATION_JSON).build();
			}
		}
		else {
			response = Response.status(Response.Status.BAD_REQUEST).entity("The user entered a wrong symbol").type(MediaType.APPLICATION_JSON).build();
		}
		
		if(response.getStatus() == Response.Status.OK.getStatusCode())
		{
			entityManager.persist(equation);
		}
	
		
		return	response;
		
		}catch(Exception e) {
			e.printStackTrace();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").type(MediaType.APPLICATION_JSON).build();
		}
	
	}
	
	
	@GET
	@Path("/calculations")
	public Response GetCalculations() {
		List<Equation> equations= new ArrayList<>();
		Response response = null;
		
		TypedQuery<Equation> query = entityManager.createQuery("SELECT e FROM Equation e", Equation.class);
		equations = query.getResultList();
		
		if(equations != null) {
			 response = Response.ok(equations).type(MediaType.APPLICATION_JSON).build();
		}
		else {
			response = Response.status(Response.Status.BAD_REQUEST).entity("There are no equations").type(MediaType.APPLICATION_JSON).build();
		}
		
		return response;
	}
	
}
