package com.example.homeworkAA.domain

import com.example.homeworkAA.data.models.Actor
import com.example.homeworkAA.data.models.Movie

class MoviesDataSource {
    fun getMovies(): List<Movie> {
        return listOf(
            Movie(
                "https://s3-alpha-sig.figma.com/img/dc12/0ed4/1c9f714c6b87b83e8cc0932e0c8576f7?Expires=1607904000&Signature=O9GUiK6h8AwlJIpViG1zdziE3gUOKC5~4c9wp8id6aexT9Vnbbi6QXpg87U7Hju~aSoJ0VWzcgVQabTGu13sD5QDNIveDfrxrX6IvQxLFHZRSvKda-lY32Xw91r2hX-i5PK-6sJV3PRsaJXFkLyUVThlLNFS2CvpCaJPETwekukbmb-2yyifQ7Q4~hhC2rtFpcZX8Nycm~Gj4qGyQBIQce~FscIctsP5V1iV42zmLvUT4dXwK1s1xX5wGJo9dSNVqPgyMvYZAl-tPxh9Fx~ec3uXcruow0jXbY8tBq5PW4J~6hrWUWN1JqZ-6PRGXwjC4PGZ8B89yqttbk9a~d7ywg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA",
                "https://s3-alpha-sig.figma.com/img/85f1/659d/0159882fd7d1a53192ba7e58f174971e?Expires=1607904000&Signature=QYHaspsVSUysIhCUUzeqoofe4Oz32F~~nIMo92I1VJEc61ST4iAkDFgtmZTT86IpPdShNm1yhl61la6~ZdIqbHaHX6a4iDw5lTigzZzNstnCAQBro5EhHRQNCvpV31GDWn2DawAu9l9GPyU3QzgbgiRvJqo8SFar9HkcejeXse65aMLtO8FaIGMiX1t-XSM~zshzfXvyr9a6-OHzcv-2muTUbUKqpta-T1IIu0ToGb7jfiORTLGhbBtjNKMOOPrPzQLiJBmDypXXZEqiRh551EAtd9rJFDJol2MhMtebmbfnR5Gdbij2QE4UPutlBExP9k8ldMidkNHh9-f5OkeCDQ__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA",
                13,
                false,
                listOf("Action", "Adventure", "Drama"),
                4F,
                125,
                "Avengers: End Game",
                137,
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\\' actions and restore balance to the universe.",
                getAvengersActors()
            ),
            Movie(
                "https://s3-alpha-sig.figma.com/img/c1ed/9c57/1175fff3a3786edca69e746f7814f968?Expires=1607904000&Signature=bw~IghnLB0Ul~~wlbje6dohiMSUDd8fUDO9HQsCK2K1V01weAKGAU7rV91~634p-aJDZjslrQhOgxfGjKiqqs~w-JTwBuwUouQDeI73KGQ00EtU~ja8ybMcYgeUV4pK3scZDiM0Ch0h77QR1cpozHKae~7vpUEcvEDHzNtgyyqDKevAGMbkuh6W-JUJ~DwqLWfY0LbdJHyHKRI5f3w94sdKRgdQ21LtUugm4WVt~MWmuPaEoz8qkxpCIC6vXDRQKULrPoqVA72w~Ah~V~2bOikvAHUN6Zlq869-rgX40teYoxpKTt7xuY7F4ltlF0Iv34RK92AwDWQVSH8peQNSsAA__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA",
                "https://api.wannart.com/storage/post/2020/05/Tenet-movie-wannagate.jpg",
                16,
                true,
                listOf("Action", "Sci-Fi", "Thriller"),
                5F,
                98,
                "Tenet",
                97,
                "A secret agent embarks on a dangerous, time-bending mission to prevent the start of World War III.",
                getTenetActors()
            ),
            Movie(
                "https://s3-alpha-sig.figma.com/img/984e/8417/6361961de2f7a2eb504cdeec809d31b7?Expires=1607904000&Signature=V2p7KayNg2LelGkYzJAfllQHKm5RNLPz-825LtXUw1QhKtCCng8yWvbFIqRr36uQ3uII3x0fMRSiSKSS~hWWKv4IpvlSHkoCYyABQt~TYgKCeJJNwJrreQytG3AWWeIxmzFMDcVf4Fp8Ii-Zwc8E8HJKPda6Y8JM0QXlnJd-IdCtjxi~rjcDbDqdYfEYTifs5kM4dACcIue6oPfcAMjoWUMerew7U4ab9AeVTpft9n3W9vB8w55zduSmR79Le0zbaaPi~sLIS6i8042HpWb2o4bgdnvBsTt3Kw5wWFhv8DMQJz6~~uBE5pZMQJbztdKE6C0PcZWmX23P-TqXFQttmw__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA",
                "https://pbs.twimg.com/media/EGrrG5RWoAA468Y.jpg",
                13,
                false,
                listOf("Action", "Adventure", "Sci-Fi"),
                4F,
                38,
                "Black Widow",
                102,
                "International spy and assassin Natasha Romanoff becomes the superhero Black Widow.",
                getBlackWidowActors()
            ),
            Movie(
                "https://s3-alpha-sig.figma.com/img/9b53/9e38/b80ee9fe84a6f1891904326a948c37c8?Expires=1607904000&Signature=cGtYnmUAOx2pUVVxGoe7QhrpSo36~hkixeiWK5ufZxqVI8IsfAmSqiulKbTb0I4myICJ3OQjd3y40AxmL-gUBKe~HctGBBy9BQg93dLHSlPFKuX4C8iNufoY~jr-jDIAK0T65NEj-UvLCfqVUpet~aoJdld1u8IwCjNFaNOuIK5~qu8UDZqzy9e0B4-gIlbRqt2w9Ev07VWcv0v3ggypVrntfvDIwKJBpwpNS35rKo24v~9IoE-kIWWnavmWzSk0GsdIQm5DUQnO~NWjWtz6J~COpUU5QNWc24hemtZZ32zl26YVwyCdc-aMN-yT~PPYsVwbje0tECjXUxTPgHm1QQ__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA",
                "https://hdfon.ru/wp-content/uploads/hdfon.ru-367157360-1024x576.jpg",
                13,
                false,
                listOf("Action", "Adventure", "Fantasy"),
                5F,
                74,
                "Wonder Woman 1984",
                120,
                "Wonder Woman squares off against Maxwell Lord and the Cheetah, a villainess who possesses superhuman strength and agility.",
                getWonderWomanActors()
            )
        )
    }

    private fun getAvengersActors(): List<Actor>{
        return listOf(
            Actor("Robert Downey Jr.", "https://s3-alpha-sig.figma.com/img/a58c/56ed/0f8f5ec204f1a37e0e15e2a731ab190a?Expires=1607904000&Signature=SWeOjEBPXMvk4AgllPX26koPnfC1qObkKvh~swrpTmJ66DqTi89HUrdgZEc3zZIc2SmBCSmhv2c-EXFYe6~imSUyQ4UUFcFXpepEa-gjOiRkT8HHw2b13UyB3SshNkr~Hg05Gpc~-UzQlrXmq-m97wlCOYNNmR~coUzRTPBfrFcGS2~xj89nzef7~0OijbLjhcSpUK4bS0wRkopW-LIlVVIsKrq1FpbkF-G1k6cLurPm6QMoQUWcCWzbV0VmThpBfVq-fokrGZcaVc3pd3HcscJz4e0RBK8VXM7zO9u8eA~DjMt5Cl8KnXhdY3WsINbz28zPQUSDUg9mfdDGNP~PUw__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA"),
            Actor("Chris Evans", "https://s3-alpha-sig.figma.com/img/95e5/6794/5c9007d7ca26a2d00d93fe8d18c89643?Expires=1607904000&Signature=BEDvd3lKRFOPgJWFm~RZ7F3yC9RSBvOH28-xYLy3fVGZROOt6ZZkO8lJ5caZirEIzrckNZxYSjqs~40fsWNCpav36tdRKjQJlcMxV4ko8XQ44hk6FNfRlb0gqnggzWqZ-vpGKU1cwDwFYKGKQdLH1w6V98jPKrPATN684NZdyJWYe7fnz2AdOsqVhLJ6G0vOVDzfxyjUyl59YQM2B1Sw11ySkgUc-q1mN52MidGbdO~GxYRVx9eZjwOaFfsbXbfeGzJRrjReffJmRWjt3-Dq3oTtxUy3949mzyoLH3OFU6JZVB3yOv3Z8q4h-yL2o95aB9IECHrTd00h34wnBRGEBQ__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA"),
            Actor("Mark Ruffalo", "https://s3-alpha-sig.figma.com/img/d3fa/bc44/eeb8d6e5b9c059a681dcbe67501dceae?Expires=1607904000&Signature=PqpCICXzpUfvJu7rN2zY8kLo-qO4c57Ngaf50Td18g-arwP8lKscrrnjyzHCeS9b3FkZWcxMe8jOXHbcy2r~Y4LYwc2bJE~HVvLRG3hEtrV3gqVWCp-ltXvHMRkRG3g0MzDtmxJZOHZYuUgJSCzb-Q5PsnDTiRJ2F5uYOz~l5UBD4gDiBSdGZAZ6uV3agim50YnJi4IKLXJ3NWeIHGoP-EpCFZUUqY8PL5visietEul5AOGBcuWiv4TzrUvclEQCgDxP5nA-cXRBBAZRvmloB1dKH3-pkrPyx6i7gluf8WNb0HyY8-x41wGfP6G1uNKItGSFwqubgfHYO1Nhh1cW5g__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA"),
            Actor("Chris Hemsworth", "https://s3-alpha-sig.figma.com/img/7bd1/c6fe/f56e2ec650a0ee6e64c9b1356e383bb3?Expires=1607904000&Signature=Hh-rvOX3OpNKmFNFM8Rp~dL42EaupGdIb54jPo6IraIW7MqXQl5vMN7BSLrv-IkzCu4P7wnwYiwC6rxOa2UNExwPiKkiaotvun7oY1ZuEOKRMImvfrUfnEgE4~Ml~o3HtZeiC8wiz34CDdBFQzb5Z9LTZsemLEPZv3eDKpSurSSvzDNvwgOV5QJZ4IikdtC0ZJdxZOlJvG1Xlc304XOMazDFB4YXKkTOi4iwX5UjxrRZyl1I7y-sYeHSfC0dYTIkj3YMY6IGuX32H454troDg0VVnHjezCV3k9Njm7gm4KWA~6wEG3CTMuMK4ZtciSagSL2S70X5p0JytoF6V8-lZA__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA"),
            Actor("Scarlett Johansson", "https://resizing.flixster.com/jDNE0xdY1Jt7z1Uvnn-2TWPLkTE=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/69369/69369_v9_bc.jpg"),
            Actor("Jeremy Renner", "https://resizing.flixster.com/_ugy3gSkwYvjIYb8_dl1T5DYiuk=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/31106/31106_v9_bb.jpg")
        )
    }

    private fun getTenetActors(): List<Actor>{
        return listOf(
            Actor("John David Washington", "https://resizing.flixster.com/vvySSo18ZG2PMUho4Psys7igR0Y=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/873014/873014_v9_bb.jpg"),
            Actor("Robert Pattinson", "https://resizing.flixster.com/okxesaV18jvENhQBL4oyarJdzqw=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/487714/487714_v9_bb.jpg"),
            Actor("Elizabeth Debicki", "https://resizing.flixster.com/s1GfGRPwMHLsFoJSIabtiD6jOoM=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/669281/669281_v9_bb.jpg"),
            Actor("Kenneth Branagh", "https://resizing.flixster.com/RldbaQS2Ykgjwi9vhiOq4GpikP0=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/194/194_v9_bb.jpg"),
            Actor("Dimple Kapadia", "https://resizing.flixster.com/8lE9p-KwKhQq8FpEGYOi84ftRUk=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/156805/156805_v9_ba.jpg"),
            Actor("Aaron Taylor-Johnson", "https://resizing.flixster.com/wWx8qQ562dWeVuBAnB50r-93ivI=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/263572/263572_v9_bb.jpg")
        )
    }

    private fun getBlackWidowActors(): List<Actor>{
        return listOf(
            Actor("Scarlett Johansson", "https://resizing.flixster.com/jDNE0xdY1Jt7z1Uvnn-2TWPLkTE=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/69369/69369_v9_bc.jpg"),
            Actor("Florence Pugh", "https://resizing.flixster.com/RWM5bNIJM-92572md1TxGdJkM64=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/872667/872667_v9_bb.jpg"),
            Actor("David Harbour", "https://resizing.flixster.com/1AVQLNDvVs969RHVs_wX4dyOOFU=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/277640/277640_v9_ba.jpg"),
            Actor("Rachel Weisz", "https://resizing.flixster.com/4FosbQHO85e8W1zTrvRaNTDKkDc=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/74113/74113_v9_bb.jpg"),
            Actor("Robert Downey Jr.", "https://resizing.flixster.com/IExT9opt_dhXWHad1qyYVc5iQsc=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/67369/67369_v9_bb.jpg"),
            Actor("Ray Winstone", "https://resizing.flixster.com/bZRwujWnnW_L0vO2fCVuf-WQNMA=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/53262/53262_v9_bb.jpg")
        )
    }

    private fun getWonderWomanActors(): List<Actor>{
        return listOf(
            Actor("Gal Gadot", "https://resizing.flixster.com/A0a3NLeHHfQX0F5HdnTQ7HQepL8=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/532761/532761_v9_bc.jpg"),
            Actor("Kristen Wiig", "https://resizing.flixster.com/Iui8iPv44B-5pDJRnR3OIQ0DF0U=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/297308/297308_v9_bb.jpg"),
            Actor("Chris Pine", "https://resizing.flixster.com/R0DUys2VlNRY7JCyMsY7h2Npiak=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/282920/282920_v9_ba.jpg"),
            Actor("Pedro Pascal", "https://resizing.flixster.com/opNlS_I0PMOzM3kBD49BrasLtG8=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/494807/494807_v9_bb.jpg"),
            Actor("Connie Nielsen", "https://resizing.flixster.com/xACnAEO3_LcSoeUVwq2-6HiBQwU=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/155555/155555_v9_ba.jpg"),
            Actor("Robin Wright", "https://resizing.flixster.com/cz0euhlg_4Nd5xNrtlpuQ6OGVBw=/506x652/v2/https://flxt.tmsimg.com/v9/AllPhotos/55876/55876_v9_bc.jpg")
        )
    }
}

