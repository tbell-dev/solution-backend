package kr.co.tbell.labeling.solutionbackend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Paging {
    // 현재 페이지 크기
    private Integer pageSize;

    // 현재 페이지 정보
    private Integer pageNumber;

    // 전체 페이지 수
    private Integer totalPages;

    // 결과 데이터 수
    private Integer numberOfElements;

    // 마지막 페이지 여부
    private boolean last;

    // 첫번째 페이지 여부
    private boolean first;

    // 빈 페이지 여부
    private boolean empty;

    // 이전 페이지 존재 여부
    private boolean prev;

    // 다음 페이지 존재 여부
    private boolean next;
}
