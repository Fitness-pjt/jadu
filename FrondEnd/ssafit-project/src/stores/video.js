import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useVideoStore = defineStore("video", () => {
    const videoList = ref([]);
    const selectedVideos = ref([])

    const youtubeSearch = (keyword) => {
        const url = 'https://www.googleapis.com/youtube/v3/search';
        const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY;

        axios({
            url,
            method: "GET",
            params: {
                key: API_KEY,
                part: "snippet", // 검색 시 필요한 필수 매개변수
                maxResults: 2,
                order: "relevance",
                q: keyword,
                type: "video",
                videoDuration: "medium"
            },
        }).then((res) => {
              console.log("res.data", res.data);

            videoList.value = res.data.items;
        });
    };

    const selectVideo = ref(null);

    const clickVideo = (video) => {
        selectVideo.value = video;
    };

    function toggleVideoSelection(video) {
        const index = selectedVideos.value.findIndex(v => v.id.videoId === video.id.videoId)
        if (index === -1) {
            selectedVideos.value.push(video)
        } else {
            selectedVideos.value.splice(index, 1)
        }
    }

    function clearSelectedVideos() {
        selectedVideos.value = [];
        selectVideo.value = null;
    };
    return {
        youtubeSearch, videoList, clickVideo, selectVideo, selectedVideos,
        toggleVideoSelection,clearSelectedVideos
    };
});