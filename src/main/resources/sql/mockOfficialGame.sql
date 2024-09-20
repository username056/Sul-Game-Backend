-- 트랜잭션 시작
START TRANSACTION;

-- 1. '프라이팬 놀이'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '프라이팬 놀이',
             NULL,
             '박자에 맞춰 이름을 부르며 상대방을 지목하는 게임입니다. 박자를 놓치거나 이름을 잘못 부르면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id1 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id1,
             '팅팅팅팅 탱탱탱탱 팅팅탱탱 프라이팬 놀이',
             NULL,
             TRUE,
             0,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id1, 'TAG1'),
      (@base_post_id1, 'TAG2');

-- 2. '눈치게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '눈치게임',
             NULL,
             '참가자들이 순서 없이 숫자를 외치며, 동시에 숫자를 외치거나 순서를 놓치면 벌칙을 받는 게임입니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id2 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id2,
             NULL,
             NULL,
             FALSE,
             1,
             2,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id2, 'TAG3'),
      (@base_post_id2, 'TAG4');

-- 3. '3-6-9'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '3-6-9',
             NULL,
             '순서대로 숫자를 세며 3, 6, 9가 들어간 숫자에서 박수를 치는 게임입니다. 실수하면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id3 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id3,
             NULL,
             NULL,
             FALSE,
             0,
             1,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id3, 'TAG5'),
      (@base_post_id3, 'TAG6');

-- 4. '아이엠 그라운드'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '아이엠 그라운드',
             NULL,
             '리듬에 맞춰 동작을 취하며 다른 사람을 지목하는 게임입니다. 실수하면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id4 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id4,
             '아 아이 아이엠 아이엠그 아이엠그라 아이엠그라운 아이엠그라운드',
             NULL,
             TRUE,
             1,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id4, 'TAG7'),
      (@base_post_id4, 'TAG8');

-- 5. '병뚜껑 놀이'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '병뚜껑 놀이',
             NULL,
             '병뚜껑을 튕겨 상대방의 병뚜껑을 맞추는 게임입니다. 맞추면 상대방이 술을 마십니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id5 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id5,
             NULL,
             NULL,
             FALSE,
             0,
             0,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id5, 'TAG9'),
      (@base_post_id5, 'TAG10');

-- 6. '손병호 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '손병호 게임',
             NULL,
             '질문에 대답하지 않고 손가락을 접어가며 진행하는 게임입니다. 모든 손가락이 접히면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id6 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id6,
             NULL,
             NULL,
             FALSE,
             0,
             1,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id6, 'TAG11'),
      (@base_post_id6, 'TAG12');

-- 7. '무궁화 꽃이 피었습니다'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '무궁화 꽃이 피었습니다',
             NULL,
             '술래가 뒤를 돌아볼 때 움직이다 걸리면 벌칙을 받는 게임입니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id7 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id7,
             '무궁화 꽃이 피었습니다',
             NULL,
             TRUE,
             0,
             1,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id7, 'TAG13'),
      (@base_post_id7, 'TAG14');

-- 8. '더 게임 오브 데스'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '더 게임 오브 데스',
             NULL,
             '손가락으로 숫자를 가리키며 상대방을 지목하는 게임입니다. 실수하면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id8 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id8,
             NULL,
             NULL,
             FALSE,
             1,
             2,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id8, 'TAG1'),
      (@base_post_id8, 'TAG3');

-- 9. '바니바니'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '바니바니',
             NULL,
             '''바니바니''를 외치며 상대방을 지목하고, 옆 사람들은 ''당근당근''을 외치는 게임입니다. 실수하면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id9 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id9,
             NULL,
             NULL,
             FALSE,
             1,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id9, 'TAG2'),
      (@base_post_id9, 'TAG4');

-- 10. '소맥 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '소맥 게임',
             NULL,
             '소주와 맥주를 섞어 만든 소맥의 비율을 맞추는 게임입니다. 가장 정확한 비율을 만든 사람이 승리합니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id10 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id10,
             NULL,
             NULL,
             FALSE,
             1,
             0,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id10, 'TAG5'),
      (@base_post_id10, 'TAG7');

-- 11. '참참참'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '참참참',
             NULL,
             '술래가 ''참참참''을 외치며 좌우를 가리키고, 다른 사람들은 반대 방향을 가리켜야 합니다. 틀리면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id11 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id11,
             NULL,
             NULL,
             FALSE,
             0,
             0,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id11, 'TAG6'),
      (@base_post_id11, 'TAG8');

-- 12. '369 박수 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '369 박수 게임',
             NULL,
             '3, 6, 9가 들어간 숫자에서 박수를 치는 게임입니다. 실수하면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id12 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id12,
             NULL,
             NULL,
             FALSE,
             0,
             1,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id12, 'TAG9'),
      (@base_post_id12, 'TAG10');

-- 13. '훈민정음 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '훈민정음 게임',
             NULL,
             '제시된 초성으로 시작하는 단어를 빠르게 말하는 게임입니다. 생각나지 않으면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id13 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id13,
             '훈민정음 훈민정음 훈민정음 훈민정음~',
             NULL,
             TRUE,
             1,
             1,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id13, 'TAG11'),
      (@base_post_id13, 'TAG12');

-- 14. '007 빵!'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '007 빵!',
             NULL,
             '순서대로 0, 0, 7을 외치고 마지막 사람이 ''빵!''을 외치며 다른 사람을 지목합니다. 실수하면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id14 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id14,
             NULL,
             NULL,
             FALSE,
             0,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id14, 'TAG13'),
      (@base_post_id14, 'TAG14');

-- 15. '딸기게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '딸기게임',
             NULL,
             '''딸기''를 연속해서 말하다가 갑자기 ''딸기''가 아닌 다른 과일 이름을 말하는 게임입니다. 실수하면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id15 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id15,
             NULL,
             NULL,
             FALSE,
             0,
             1,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id15, 'TAG1'),
      (@base_post_id15, 'TAG5');

-- 16. '공동묘지 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '공동묘지 게임',
             NULL,
             '인트로 후 ''아~''를 외치며 손을 흔들고, ''쇼크!''를 외치며 다른 사람에게 토스합니다. 옆 사람들은 ''으악!''을 외치며 만세 동작을 합니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id16 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id16,
             '공동묘지에~올라갔더니~시체가 벌떡 시체가 벌떡 벌떡 벌떡 벌떡 벌떡 벌떡',
             NULL,
             TRUE,
             1,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id16, 'TAG2'),
      (@base_post_id16, 'TAG6');

-- 17. '혼자 왔어요 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '혼자 왔어요 게임',
             NULL,
             '인원수를 늘렸다 줄이며 ''혼자 왔어요'', ''둘이 왔어요'' 등을 외치는 게임입니다. 순서를 틀리면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id17 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id17,
             '빠! 빠! 빠빠빠! 빠빠! 빠빠! 빠빠빠!',
             NULL,
             FALSE,
             0,
             1,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id17, 'TAG3'),
      (@base_post_id17, 'TAG7');

-- 18. '지하철 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '지하철 게임',
             NULL,
             '특정 호선의 역 이름을 순서대로 말하는 게임입니다. 생각나지 않으면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id18 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id18,
             '지~하철 지하철 지하철 지하철',
             NULL,
             FALSE,
             1,
             1,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id18, 'TAG4'),
      (@base_post_id18, 'TAG8');

-- 19. '경마 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '경마 게임',
             NULL,
             '순서대로 번호를 외치며 점점 속도를 높이는 게임입니다. 박자를 놓치면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id19 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id19,
             '달리고~달리고~달리고 달리고 달리고',
             NULL,
             FALSE,
             1,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id19, 'TAG5'),
      (@base_post_id19, 'TAG9');

-- 20. '만두 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '만두 게임',
             NULL,
             '모두가 손을 만두 모양으로 오므리고, 시작하는 사람이 5단위 숫자를 말합니다. 그 수만큼 손가락이 펴져있으면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id20 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id20,
             '만두 만두 만두 만~두!',
             NULL,
             FALSE,
             0,
             1,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id20, 'TAG6'),
      (@base_post_id20, 'TAG10');

-- 21. '사랑의 총알을 누구에게 쏠까요'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '사랑의 총알을 누구에게 쏠까요',
             NULL,
             '두 손가락으로 두 명을 지목하고, 시작하는 사람이 지정한 손부터 이어나갑니다. 총알이 없어지면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id21 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id21,
             '사랑의~총알을~누구에게 쏠까요 빠방!',
             NULL,
             FALSE,
             1,
             2,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id21, 'TAG7'),
      (@base_post_id21, 'TAG11');

-- 22. '홍삼 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '홍삼 게임',
             NULL,
             '두 사람을 지목하고, 같은 사람을 지목하면 그 사람이 특정 동작을 합니다. 다른 사람들은 그 동작을 따라합니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id22 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id22,
             '아~싸 홍삼 에브리바디 홍삼',
             NULL,
             FALSE,
             1,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id22, 'TAG8'),
      (@base_post_id22, 'TAG12');

-- 23. '두부 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '두부 게임',
             NULL,
             '자신은 ''두부 세 모''이고, 오른쪽은 ''네 모'', 왼쪽은 ''한 모''입니다. ''두부 □모''를 외치며 게임을 진행합니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id23 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id23,
             '두~부 두부 두부 으쌰 으쌰 으쌰 으쌰',
             NULL,
             FALSE,
             2,
             1,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id23, 'TAG9'),
      (@base_post_id23, 'TAG13');

-- 24. '김삿갓 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '김삿갓 게임',
             NULL,
             '''김''과 ''삿갓''을 번갈아 외치며, 횟수를 점점 늘려갑니다. 순서를 틀리면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id24 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id24,
             '김 삿갓 김 삿갓 김 김 삿갓 삿갓',
             NULL,
             FALSE,
             1,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id24, 'TAG10'),
      (@base_post_id24, 'TAG14');

-- 25. '아파트 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '아파트 게임',
             NULL,
             '모두가 동시에 손을 공중에 모아 층을 만들고, 시작하는 사람이 말한 숫자에 해당하는 층에 손을 얹은 사람이 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id25 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id25,
             '아~파트 아파트 아~파트 아파트',
             NULL,
             FALSE,
             0,
             0,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id25, 'TAG1'),
      (@base_post_id25, 'TAG4');

-- 26. '잔치기 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '잔치기 게임',
             NULL,
             '소주잔을 치는 횟수에 따라 게임의 진행 방향이 바뀝니다. 자기 차례가 아닌데 잔을 치면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id26 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id26,
             '잔치기 잔치기 잔잔잔',
             NULL,
             FALSE,
             1,
             1,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id26, 'TAG2'),
      (@base_post_id26, 'TAG5');

-- 27. '고백점프'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '고백점프',
             NULL,
             '3의 배수일 때 ''Go'', ''Back'', ''Jump'' 중 하나를 외치는 게임입니다. 실수하면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id27 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id27,
             '고백점프 고백점프~ 고백점프 고백점프',
             NULL,
             FALSE,
             1,
             1,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id27, 'TAG3'),
      (@base_post_id27, 'TAG6');

-- 28. '스마트폰 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '스마트폰 게임',
             NULL,
             '각자 스마트폰을 사용하여 특정 앱을 실행하고, 정해진 시간 내에 미션을 완료하는 게임입니다. 미션 실패 시 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id28 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id28,
             NULL,
             NULL,
             FALSE,
             1,
             2,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id28, 'TAG4'),
      (@base_post_id28, 'TAG7');

-- 29. '풍선 터뜨리기'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '풍선 터뜨리기',
             NULL,
             '참가자들이 서로에게 풍선을 던지고, 터뜨리지 않으면 벌칙을 받는 게임입니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id29 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id29,
             NULL,
             NULL,
             FALSE,
             0,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id29, 'TAG5'),
      (@base_post_id29, 'TAG8');

-- 30. '종이컵 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '종이컵 게임',
             NULL,
             '종이컵을 일정한 패턴으로 쌓아가며, 실수하면 벌칙을 받는 게임입니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id30 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id30,
             NULL,
             NULL,
             FALSE,
             0,
             0,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id30, 'TAG6'),
      (@base_post_id30, 'TAG9');

-- 31. '빠른 손 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '빠른 손 게임',
             NULL,
             '순간적으로 빠르게 손을 움직여 특정 동작을 완수하는 게임입니다. 실패 시 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id31 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id31,
             NULL,
             NULL,
             FALSE,
             1,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id31, 'TAG7'),
      (@base_post_id31, 'TAG10');

-- 32. '물컵 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '물컵 게임',
             NULL,
             '물컵을 일정한 높이에서 떨어뜨려서 다른 컵에 맞추는 게임입니다. 실패 시 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id32 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id32,
             NULL,
             NULL,
             FALSE,
             0,
             0,
             1
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id32, 'TAG8'),
      (@base_post_id32, 'TAG11');

-- 33. '빙고 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '빙고 게임',
             NULL,
             '빙고 카드를 사용하여 특정 패턴을 완성하는 게임입니다. 패턴을 완성하지 못하면 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id33 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id33,
             NULL,
             NULL,
             FALSE,
             1,
             2,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id33, 'TAG9'),
      (@base_post_id33, 'TAG12');

-- 34. '퀴즈 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '퀴즈 게임',
             NULL,
             '참가자들에게 술과 관련된 퀴즈를 내고, 정답을 맞추지 못하면 벌칙을 받는 게임입니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id34 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id34,
             NULL,
             NULL,
             FALSE,
             1,
             1,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id34, 'TAG10'),
      (@base_post_id34, 'TAG13');

-- 35. '춤추기 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '춤추기 게임',
             NULL,
             '음악에 맞춰 춤을 추다가 멈춰야 할 때 춤을 멈추지 못하면 벌칙을 받는 게임입니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id35 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id35,
             NULL,
             NULL,
             FALSE,
             0,
             1,
             2
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id35, 'TAG1'),
      (@base_post_id35, 'TAG14');

-- 36. '공 던지기 게임'
INSERT INTO base_post (
    dtype,
    title,
    introduction,
    description,
    likes,
    liked_member_ids,
    views,
    reported_count,
    member_member_id,
    daily_score,
    weekly_score,
    total_score,
    comment_count,
    source_type,
    thumbnail_icon,
    is_creator_info_private
) VALUES (
             'OfficialGame',
             '공 던지기 게임',
             NULL,
             '공을 멀리 던져서 특정 목표물에 맞추는 게임입니다. 실패 시 벌칙을 받습니다.',
             0,
             NULL,
             0,
             0,
             2,
             0,
             0,
             0,
             0,
             1,
             NULL,
             FALSE
         );

SET @base_post_id36 = LAST_INSERT_ID();

INSERT INTO official_game (
    base_post_id,
    intro_lyrics_in_game_post,
    intro_media_file_in_game_post_url,
    is_intro_exist,
    level_tag,
    head_count_tag,
    noise_level_tag
) VALUES (
             @base_post_id36,
             NULL,
             NULL,
             FALSE,
             0,
             0,
             0
         );

INSERT INTO official_game_game_tags (
    official_game_base_post_id,
    game_tags
) VALUES
      (@base_post_id36, 'TAG2'),
      (@base_post_id36, 'TAG1');

-- 모든 삽입 작업 완료 후 트랜잭션 커밋
COMMIT;
