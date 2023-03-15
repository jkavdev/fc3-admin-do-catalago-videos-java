package br.com.jkavdev.fullcycle.admin.catalogo.e2e;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.Identifier;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberType;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreID;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.UpdateCastMemberRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models.CategoryResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models.CreateCategoryRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models.UpdateCategoryRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.Json;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.GenreResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.UpdateGenreRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockDsl {

    MockMvc mvc();

    default CastMemberID givenACastMember(final String aName, final CastMemberType aType) throws Exception {
        final var aRequestBody = new CreateCastMemberRequest(aName, aType);
        return CastMemberID.from(this.given("/cast_members", aRequestBody));
    }

    default ResultActions givenACastMemberResult(final String aName, final CastMemberType aType) throws Exception {
        final var aRequestBody = new CreateCastMemberRequest(aName, aType);
        return this.givenResult("/cast_members", aRequestBody);
    }

    default ResultActions updateACastMember(final CastMemberID anId, final String aName, final CastMemberType aType) throws Exception {
        return this.update("/cast_members/", anId, new UpdateCastMemberRequest(aName, aType));
    }

    default CastMemberResponse retrieveACastMember(final CastMemberID anId) throws Exception {
        return this.retrieve("/cast_members/", anId, CastMemberResponse.class);
    }

    default ResultActions retrieveACastMemberResult(final CastMemberID anId) throws Exception {
        return this.retrieveResult("/cast_members/", anId);
    }

    default ResultActions deleteACastMember(final CastMemberID anId) throws Exception {
        return this.delete("/cast_members/", anId);
    }

    default ResultActions listCastMembers(final int page, final int perPage) throws Exception {
        return this.listCastMembers(page, perPage, "", "", "");
    }

    default ResultActions listCastMembers(final int page, final int perPage, final String search) throws Exception {
        return this.listCastMembers(page, perPage, search, "", "");
    }

    default ResultActions listCastMembers(final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {
        return this.list("/cast_members", page, perPage, search, sort, direction);
    }

    default GenreID givenAGenre(final String aName, final boolean isActive, final List<CategoryID> categories) throws Exception {
        final var aRequestBody = new CreateGenreRequest(aName, mapTo(categories, CategoryID::getValue), isActive);

        return GenreID.from(this.given("/genres", aRequestBody));
    }

    default GenreResponse retrieveAGenre(final GenreID anId) throws Exception {
        return this.retrieve("/genres/", anId, GenreResponse.class);
    }

    default ResultActions deleteAGenre(final GenreID anId) throws Exception {
        return this.delete("/genres/", anId);
    }

    default ResultActions updateAGenre(final GenreID anId, UpdateGenreRequest body) throws Exception {
        return this.update("/genres/", anId, body);
    }

    default ResultActions listGenres(final int page, final int perPage) throws Exception {
        return this.listGenres(page, perPage, "", "", "");
    }

    default ResultActions listGenres(final int page, final int perPage, final String search) throws Exception {
        return this.listGenres(page, perPage, search, "", "");
    }

    default ResultActions listGenres(final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {
        return this.list("/genres", page, perPage, search, sort, direction);
    }

    default CategoryID givenACategory(final String aName, final String aDescription, final boolean isActive) throws Exception {
        final var aRequestBody = new CreateCategoryRequest(aName, aDescription, isActive);

        return CategoryID.from(this.given("/categories", aRequestBody));
    }

    default CategoryResponse retrieveACategory(final CategoryID anId) throws Exception {
        return this.retrieve("/categories/", anId, CategoryResponse.class);
    }

    default ResultActions deleteACategory(final CategoryID anId) throws Exception {
        return this.delete("/categories/", anId);
    }

    default ResultActions updateACategory(final CategoryID anId, UpdateCategoryRequest body) throws Exception {
        return this.update("/categories/", anId, body);
    }

    default ResultActions listCategories(final int page, final int perPage) throws Exception {
        return this.listCategories(page, perPage, "", "", "");
    }

    default ResultActions listCategories(final int page, final int perPage, final String search) throws Exception {
        return this.listCategories(page, perPage, search, "", "");
    }

    default ResultActions listCategories(final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {
        return list("/categories", page, perPage, search, sort, direction);
    }

    default <A, D> List<D> mapTo(final List<A> actual, final Function<A, D> mapper) {
        return actual.stream()
                .map(mapper)
                .toList();
    }

    private String given(final String url, final Object body) throws Exception {
        final var aRequest = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        String actualId = this.mvc().perform(aRequest)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse().getHeader("Location")
                .replace("%s/".formatted(url), "");

        return actualId;
    }

    private ResultActions givenResult(final String url, final Object body) throws Exception {
        final var aRequest = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        return this.mvc().perform(aRequest);
    }

    private ResultActions list(final String url, final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {
        final var aRequest = get(url)
                .queryParam("page", String.valueOf(page))
                .queryParam("perPage", String.valueOf(perPage))
                .queryParam("sort", sort)
                .queryParam("dir", direction)
                .queryParam("search", search);

        return this.mvc().perform(aRequest)
                .andDo(print());
    }

    private <T> T retrieve(final String url, final Identifier anId, final Class<T> clazz) throws Exception {
        final var aRequest = get(url + anId.getValue())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        final var json = this.mvc().perform(aRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return Json.readValue(json, clazz);
    }

    private ResultActions retrieveResult(final String url, final Identifier anId) throws Exception {
        final var aRequest = get(url + anId.getValue())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc().perform(aRequest);
    }

    private ResultActions delete(final String url, final Identifier anId) throws Exception {
        final var aRequest = MockMvcRequestBuilders.delete(url + anId.getValue())
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc().perform(aRequest);
    }

    private ResultActions update(final String url, final Identifier anId, Object body) throws Exception {
        final var aRequest = put(url + anId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        return this.mvc().perform(aRequest);
    }

}
