--
-- Created by IntelliJ IDEA.
-- User: hzk
-- Date: 2019/7/3
-- Time: 18:31
-- To change this template use File | Settings | File Templates.
--
local lockKey = KEYS[1]
local lockValue = KEYS[2]

local result_get = redis.call('get',lockKey);
if lockValue == result_get then
    local result_del = redis.call('del',lockKey)
    return result_del
else
    return false;
end
