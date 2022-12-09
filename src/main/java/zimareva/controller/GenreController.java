package zimareva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zimareva.model.Genre;
import zimareva.service.GenreService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getGenres(@RequestParam(required = false) String name) {
        List<Genre> genres = genreService.getGenres(name);
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable(value = "id") Long genreId) {
        Genre genre = genreService.getGenre(genreId);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        Genre createdGenre = genreService.addGenre(genre);
        return new ResponseEntity<>(createdGenre, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public void deleteGenre(@PathVariable(value = "id") Long genreId) {
        genreService.deleteGenre(genreId);
    }

    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Genre> partialUpdateGenre(@PathVariable("id") Long genreId,
                                                    @RequestBody Map<String, Object> genreDetails) {
        Genre editedGenre = genreService.editGenre(genreId, genreDetails);
        return new ResponseEntity<>(editedGenre, HttpStatus.OK);
    }
}
