package org.exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @Path for accessing and modifying books
 */

@Path("/books")
public class BookResource {

  public static List<String> books = new ArrayList<>();

  /**
   * @getBooks returns all books in list
   */

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Response getBooks() {
    return Response.ok(books).build();
  }

  /**
   * @countBook returns amount of books in list
   */

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/amount")
  public Integer countBooks() {
    return books.size();
  }

  /**
   * @return returns new books list
   * @createBook add new book to list
   */

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.TEXT_PLAIN)
  public Response createBook(final String newBook) {
    books.add(newBook);
    return Response.ok(books).build();
  }

  /**
   * @param bookToUpdate title of book that should be updated
   * @param updateBook   new title for book
   * @return returns updated book list
   */

  @PUT
  @Path("{bookToUpdate}")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.TEXT_PLAIN)
  public Response updateBook(
      @PathParam("bookToUpdate") final String bookToUpdate,
      @QueryParam("book") final String updateBook) {
    books = books.stream().map(book -> {
      if (book.equals(bookToUpdate)) {
        return updateBook;
      }
      return book;
    }).collect(Collectors.toList());
    return Response.ok(books).build();
  }

  /**
   * @param bookToDelete title of book that should be deleted
   * @return returns either successful no content anymore Response when deleted or 400 when not
   * being able to remove
   */

  @DELETE
  @Path("{bookToDelete}")
  @Consumes(MediaType.TEXT_PLAIN)
  public Response deleteBook(
      @PathParam("bookToDelete") final String bookToDelete) {
    final boolean removed = books.remove(bookToDelete);
    return removed ? Response.noContent().build() :
        Response.status(Status.BAD_REQUEST).build();
  }
}