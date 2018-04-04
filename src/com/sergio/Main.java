package com.sergio;

import java.util.List;
import java.util.Scanner;

import com.sergio.model.Artist;
import com.sergio.model.DataSource;
import com.sergio.model.SongArtist;

public class Main {

	public static void main(String[] args) {
		DataSource datasource = new DataSource();
		if (!datasource.open()) {
			System.out.println("can't open datasource");
			return;
		}

		List<Artist> artists = datasource.queryArtists(DataSource.ORDER_BY_NONE);
		if (artists == null) {
			System.out.println("no artists!");
			return;
		}

		for (Artist artist : artists) {
			System.out.println("ID: " + artist.getId() + ", name: " + artist.getName());
		}

		List<String> albumsForArtists = datasource.queryAlbumsForArtist("Carole King", DataSource.ORDER_BY_ASC);
		for (String album : albumsForArtists) {
			System.out.println(album);
		}

		List<SongArtist> songArtists = datasource.queryArtistsForSong("Go Your Own Way", DataSource.ORDER_BY_ASC);
		if (songArtists == null) {
			System.out.println("Couldn't find the artist for the song");
			return;
		}
		for (SongArtist artist : songArtists) {
			System.out.println("Artist name = " + artist.getArtistName() + ", Track = " + artist.getTrack());

		}

		datasource.querySongsMetadata();

		int count = datasource.getCount(DataSource.TABLE_SONGS);
		System.out.println("Number of songs is: " + count);
		datasource.createViewForSongArtists();

		
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter a song title.");
		String title = scanner.nextLine();
		scanner.close();
		songArtists = datasource.querySongInfoView(title);
		if (songArtists.isEmpty()) {
			System.out.println("couldn't find the artist for the song");
			return;
		}

		for (SongArtist artist : songArtists) {
			System.out.println("FROM VIEW - Artist name = " + artist.getArtistName() + "Album name= "
					+ artist.getAlbumName() + "Track number= " + artist.getTrack());
		}

		datasource.insertSong("Bird Dog", "Everly Dogs", "Bob Dylan's Greatest Hits", 5);
		
		datasource.close();
	}

}
