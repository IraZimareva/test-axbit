package zimareva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import zimareva.exception.EntityNotFoundException;
import zimareva.model.Genre;
import zimareva.repository.GenreRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre getGenre(Long id) {
        return genreRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Genre.class.getName(), id));
    }

    public List<Genre> getGenres(String name) {
        if (name == null) {
            return getAllGenres();
        }
        return getGenresByParameters(name);
    }

    public List<Genre> getAllGenres() {
        return StreamSupport
                .stream(genreRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Genre> getGenresByParameters(String name) {
        return genreRepository.findByName(name);
    }

    public void deleteGenre(Long id) {
        Genre genre = getGenre(id);
        genreRepository.delete(genre);
    }

    @Transactional
    public Genre editGenre(Long id, Map<String, Object> genreDetails) {
        Genre genreToEdit = getGenre(id);
        genreDetails.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Genre.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, genreToEdit, value);
        });
        return genreToEdit;
    }
}
