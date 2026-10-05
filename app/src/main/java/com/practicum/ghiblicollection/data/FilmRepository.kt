package com.practicum.ghiblicollection.data

import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.repository.FilmRepository

class FilmRepository : FilmRepository {

    private val films = listOf(
        Film(
            id = "2baf70d1-42bb-4437-b551-e5fed5a87abe",
            title = "Castle in the Sky",
            originalTitle = "天空の城ラピュタ",
            originalTitleRomanised = "Tenkū no shiro Rapyuta",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/npOnzAbLh6VOIu3naU5QaEcTepo.jpg",
            movieBanner = "https://image.tmdb.org/t/p/w533_and_h300_bestv2/3cyjYtLWCBE1uvWINHFsFnE8LUK.jpg",
            description = "The orphan Sheeta inherited a mysterious crystal that links her to the mythical sky-kingdom of Laputa. With the help of resourceful Pazu and a rollicking band of sky pirates, she makes her way to the ruins of the once-great civilization. Sheeta and Pazu must outwit the evil Muska, who plans to use Laputa's science to make himself ruler of the world.",
            director = "Hayao Miyazaki",
            producer = "Isao Takahata",
            releaseDate = "1986",
            runningTime = "124",
            rtScore = "95"
        ),
        Film(
            id = "58611129-2dbc-4a81-a72f-77ddfc1b1b49",
            title = "My Neighbor Totoro",
            originalTitle = "となりのトトロ",
            originalTitleRomanised = "Tonari no Totoro",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/rtGDOeG9LzoerkDGZF9dnVeLppL.jpg",
            movieBanner = "https://image.tmdb.org/t/p/w533_and_h300_bestv2/5lcuYd5yHfCHWvycLTAsMEP8Dcp.jpg",
            description = "Two sisters move to the country with their father in order to be closer to their hospitalized mother, and discover the surrounding trees are inhabited by Totoros, magical spirits of the forest. When the youngest runs away from home, the older sister seeks help from the spirits to find her.",
            director = "Hayao Miyazaki",
            producer = "Toru Hara",
            releaseDate = "1988",
            runningTime = "86",
            rtScore = "93"
        ),
        Film(
            id = "ea660b10-85c4-4ae3-8a5f-41cea3648e3e",
            title = "Kiki's Delivery Service",
            originalTitle = "魔女の宅急便",
            originalTitleRomanised = "Majo no Takkyūbin",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/npOnzAbLh6VOIu3naU5QaEcTepo.jpg",
            movieBanner = "https://image.tmdb.org/t/p/w533_and_h300_bestv2/zgFCHhZKMZBOFhWJvHjywCkYcPg.jpg",
            description = "A young witch, on her mandatory year of independent life, finds fitting into a new community difficult while she supports herself by running an air courier service.",
            director = "Hayao Miyazaki",
            producer = "Hayao Miyazaki",
            releaseDate = "1989",
            runningTime = "102",
            rtScore = "96"
        ),
        Film(
            id = "0440483e-ca0e-4120-8c50-4c8cd9b965d6",
            title = "Princess Mononoke",
            originalTitle = "もののけ姫",
            originalTitleRomanised = "Mononoke Hime",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/cMYCDADoLKLbB83g4WnJemaqSd6.jpg",
            movieBanner = "https://image.tmdb.org/t/p/w533_and_h300_bestv2/kbOZyb8UDAxaSF4yK4zV9UJWuPu.jpg",
            description = "Ashitaka, a prince of the disappearing Ainu tribe, is cursed by a demonized boar god and must journey to the west to find a cure. Along the way, he encounters San, a young human woman fighting to protect the forest, and Lady Eboshi, who is trying to destroy it.",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            releaseDate = "1997",
            runningTime = "134",
            rtScore = "93"
        ),
        Film(
            id = "dc2e6bd1-8156-4886-adff-b39e6043af0c",
            title = "Spirited Away",
            originalTitle = "千と千尋の神隠し",
            originalTitleRomanised = "Sen to Chihiro no Kamikakushi",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            movieBanner = "https://image.tmdb.org/t/p/w533_and_h300_bestv2/Ab8mkHmkYADjU7wQiOkia9BzGvS.jpg",
            description = "Spirited Away is an Oscar winning Japanese animated film about a ten year old girl who wanders away from her parents along a path that leads to a world ruled by strange and unusual monster-like animals. Her parents have been changed into pigs along with them, engulfed in a world that she must rescue them from.",
            director = "Hayao Miyazaki",
            producer = "Toshio Suzuki",
            releaseDate = "2001",
            runningTime = "124",
            rtScore = "97"
        )
    )

    override suspend fun getFilms(): List<Film> = films

    override suspend fun getFilmById(id: String): Film? =
        films.firstOrNull { it.id == id }
}