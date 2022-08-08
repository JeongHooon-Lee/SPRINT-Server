package sprint.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sprint.server.controller.datatransferobject.request.CreateFriendsResultRequest;
import sprint.server.controller.datatransferobject.request.CreateFriendsRequest;
import sprint.server.controller.datatransferobject.request.DeleteFriendsRequest;
import sprint.server.controller.datatransferobject.request.LoadFriendsRequset;
import sprint.server.controller.datatransferobject.response.*;
import sprint.server.domain.Member;
import sprint.server.domain.friends.Friends;
import sprint.server.repository.FriendsRepository;
import sprint.server.service.FriendsService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FriendsApiController {
    private final FriendsService friendsService;
    private final FriendsRepository friendsRepository;
    @PostMapping("/api/friends")
    public CreateFriendsResponse createFriends(@RequestBody @Valid CreateFriendsRequest request) {
        Friends friends = friendsService.FriendsRequest(request.getSourceUserId(), request.getTargetUserId());
        return new CreateFriendsResponse(friendsRepository.existsById(friends.getId()));
    }

    @PostMapping("/api/friends/accept")
    public CreateFriendsResultResponse AcceptFriends(@RequestBody @Valid CreateFriendsResultRequest request) {
        return new CreateFriendsResultResponse(friendsService.AcceptFriendsRequest(request.getSourceUserId(), request.getTargetUserId()));
    }

    @PutMapping("/api/friends/reject")
    public CreateFriendsResultResponse RejectFriends(@RequestBody @Valid CreateFriendsResultRequest request) {
        return new CreateFriendsResultResponse(friendsService.RejectFriendsRequest(request.getSourceUserId(), request.getTargetUserId()));
    }

    @PutMapping("/api/friends/delete")
    public DeleteFriendsResponse DeleteFriends(@RequestBody @Valid DeleteFriendsRequest request) {
        return new DeleteFriendsResponse(friendsService.DeleteFriends(request.getSourceUserId(), request.getTargetUserId()));
    }


    @GetMapping("/api/friends/list")
    public LoadFriendsResponse<LoadFriendsResponseDto> LoadFriends(@RequestBody @Valid LoadFriendsRequset request) {
        List<Member> members = friendsService.LoadFriends(request.getUserId());

        List<LoadFriendsResponseDto> result = members.stream()
                .map(member -> new LoadFriendsResponseDto(member))
                .collect(Collectors.toList());
        return new LoadFriendsResponse(result.size(), result);
    }
}
