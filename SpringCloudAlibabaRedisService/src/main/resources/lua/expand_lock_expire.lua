--
-- Created by IntelliJ IDEA.
-- User: hzk
-- Date: 2019/7/4
-- Time: 15:19
-- To change this template use File | Settings | File Templates.
--
local lockKey = KEYS[1]
local lockValue = KEYS[2]
local expire = KEYS[3]

local result_get = redis.call('GET',lockKey);
if lockValue == result_get then
    local result_expire = redis.call('EXPIRE',lockKey,expire)
    return result_expire
else
    return false;
end
