insert into poll (poll_id, question) values (1, 'What is your favorite color?');
insert into option (option_id, option_value, poll_id) values (1, 'Red', 1);
insert into option (option_id, option_value, poll_id) values (2, 'Blue', 1);
insert into option (option_id, option_value, poll_id) values (3, 'Green', 1);

insert into poll (poll_id, question) values (2, 'Best Netflix original?');
insert into option (option_id, option_value, poll_id) values (4, 'Stranger Things', 2);
insert into option (option_id, option_value, poll_id) values (5, 'Black Mirror', 2);
insert into option (option_id, option_value, poll_id) values (6, 'The Crown', 2);

-- add at least ~15 polls similarly for pagination testing...
