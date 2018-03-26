using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Arts.Buzz.Web.Service
{
    public partial class beacon
    {
        public virtual IList<TriggerContent> triggercontent { get; set; }
    }

    public partial class TriggerContent
    {
        public trigger trigger { get; set; }
        public content_ex content { get; set; }
    }
}