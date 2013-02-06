package com.example.androidjsontest.bean;

import java.util.List;

public class ChannelContentsResponse {
	 private String categoryDescription;
     private String categoryID;
     private String categoryName;
     private List<ChannelContents> contents;
     
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<ChannelContents> getContents() {
		return contents;
	}
	public void setContents(List<ChannelContents> contents) {
		this.contents = contents;
	}

}
// json utilizado para o bean de channelContents e channelContentsResponse

/*    {
        "categoryDescription" : "Canal Kids",
        "categoryID" : "canalkidsideiastv",
        "categoryName" : "CanalKidsIdeiasTV",
        "contents" :

        [
			{
                "name" : "Conheça o Canal Kids!",
        "description" : "Divulgação Canal Kids",
                "tag" : "4247",
                "accountType" : "free",
        
        "episodeImg" : "http://s3.amazonaws.com/s3.zeewe.com/s3/tv/series/canalkids/4247.jpg",
        
        "episodeIdiOS" : "http://hls.videos.movile.com/i/core-media/4247_playlist.smil/master.m3u8",
        
        "downloadUrl" : "http://pd.videos.movile.com/core-media/vod/4247_bitrate_400.mp4",
                "inclusionTime" : 1345053620707,
                "publishTime" : 1343606400000
            },
            {
                "name" : "Meu Amigãozão - Espinafrada",
                "description" : "Matt fica sem saída quando tem que provar o espinafre do papai. Ele e Bongo vão ter que encarar uma pegajosa aventura!",
                "tag" : "4717",
                "accountType" : "premium",
        "episodeImg" : "http://s3.amazonaws.com/s3.zeewe.com/s3/tv/series/canalkids/4717.jpg",        
        "episodeIdiOS" : "http://hls.videos.movile.com/i/core-media/4717_playlist.smil/master.m3u8",
        "downloadUrl" : "http://pd.videos.movile.com/core-media/vod/4717_bitrate_400.mp4",
                "inclusionTime" : 1345053620707,
                "publishTime" : 1343606400000
            },
            {
                "name" : "Meu Amigãozão - Espiãs do Bem",
                "description" : "Lili, sem querer, quebra o vaso da mãe. Agora a missão dessa espiã é consertar e devolver o vaso antes que alguém veja!",
                "tag" : "4831",
                "accountType" : "premium",
        "episodeImg" : "http://s3.amazonaws.com/s3.zeewe.com/s3/tv/series/canalkids/4831.jpg",        
        "episodeIdiOS" : "http://hls.videos.movile.com/i/core-media/4831_playlist.smil/master.m3u8",
        "downloadUrl" : "http://pd.videos.movile.com/core-media/vod/4831_bitrate_400.mp4",
                "inclusionTime" : 1345053620707,
                "publishTime" : 1343606400000
            }
        ]
    }*/