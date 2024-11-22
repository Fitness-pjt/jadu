// stores/video.js
import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useVideoStore = defineStore("video", () => {
    const videoList = ref([]);
    const selectedVideos = ref([]);
    const selectVideo = ref(null);
    const isLoading = ref(false);
    const error = ref(null);

    const youtubeSearch = (keyword) => {
        isLoading.value = true;
        error.value = null;

        const url = 'https://www.googleapis.com/youtube/v3/search';
        const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY;

        return axios({
            url,
            method: "GET",
            params: {
                key: API_KEY,
                part: "snippet",
                maxResults: 2,
                order: "relevance",
                q: keyword,
                type: "video",
                videoDuration: "medium"
            },
        }).then((res) => {
            console.log("res.data", res.data);
            videoList.value = res.data.items;
        }).catch((err) => {
            console.error('Error searching videos:', err);
            error.value = '비디오 검색 중 오류가 발생했습니다.';
            throw error.value;
        }).finally(() => {
            isLoading.value = false;
        });
    };

    const clickVideo = (video) => {
        selectVideo.value = video;
    };

    const toggleVideoSelection = (video) => {
        const index = selectedVideos.value.findIndex(v => v.id.videoId === video.id.videoId);
        if (index === -1) {
            selectedVideos.value.push(video);
        } else {
            selectedVideos.value.splice(index, 1);
        }
    };



    // 비디오 ID 목록으로 비디오 정보 가져오기
    const getVideosByIds = async (videoIds) => {
        try {
            isLoading.value = true;
            error.value = null;

            const url = 'https://www.googleapis.com/youtube/v3/videos';
            const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY;

            const response = await axios.get(url, {
                params: {
                    key: API_KEY,
                    part: "snippet",
                    id: videoIds.join(',')
                }
            });

            // YouTube API 응답을 프로그램에서 사용하는 형식으로 변환
            return response.data.items.map(item => ({
                id: { videoId: item.id },
                snippet: item.snippet
            }));
        } catch (err) {
            console.error('Error fetching videos:', err);
            error.value = '비디오 정보를 가져오는 중 오류가 발생했습니다.';
            throw error.value;
        } finally {
            isLoading.value = false;
        }
    };

    // 선택된 비디오 설정 (프로그램 수정 시 사용)
    const setSelectedVideos = (videos) => {
        selectedVideos.value = [...videos];
    };

    // 비디오 선택 여부 확인
    const isVideoSelected = (videoId) => {
        return selectedVideos.value.some(v => v.id.videoId === videoId);
    };
    // videoList 초기화 메서드 추가
    const clearVideoList = () => {
        videoList.value = [];
    };

    const clearSelectedVideos = () => {
        selectedVideos.value = [];
        selectVideo.value = null;
    };

    // 모든 상태 초기화
    const clearAll = () => {
        clearVideoList();
        clearSelectedVideos();
    };


    return {
        // state
        videoList,
        selectedVideos,
        selectVideo,
        isLoading,
        error,

        // actions
        youtubeSearch,
        clickVideo,
        toggleVideoSelection,
        clearSelectedVideos,
        getVideosByIds,
        setSelectedVideos,
        isVideoSelected,
        clearAll,
        clearVideoList
    };
});